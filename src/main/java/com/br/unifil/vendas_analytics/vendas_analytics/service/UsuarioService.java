package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.UsuarioAlteracaoSenhaDto;
import com.br.unifil.vendas_analytics.vendas_analytics.enums.PermissoesUsuarioEnum;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Vendedor;
import com.br.unifil.vendas_analytics.vendas_analytics.model.PermissoesUsuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.RelatoriosPowerBi;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendedorRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.PermissoesUsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.PowerBiRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.PermissoesUsuarioEnum.ADMIN;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.PermissoesUsuarioEnum.SUPER_ADMIN;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.INATIVO;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public abstract class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private PowerBiRepository powerBiRepository;

    @Autowired
    private PermissoesUsuarioRepository permissoesUsuarioRepository;

    private final ValidacaoException USUARIO_NAO_EXISTENTE_EXCEPTION = new ValidacaoException("O usuário não existe");

    private static final List<PermissoesUsuarioEnum> PERMISSOES_ADMIN = Arrays.asList(ADMIN, SUPER_ADMIN);

    public void salvarUsuario(Usuario usuario) throws ValidacaoException {
        if (isNovoCadastro(usuario)) {
            usuario.setSituacao(ATIVO);
        }
        usuario.setDataCadastro(LocalDateTime.now());
        validaUsuario(usuario);
        usuario = validarTrocaDeSituacao(usuario);
        usuario = verificarDataUltimoAcesso(usuario);
        usuarioRepository.save(usuario);
    }

    public void validaUsuario(Usuario usuario) throws ValidacaoException {
        validarVendedorExistente(usuario);
        validaEmailClienteESituacao(usuario);
    }

    public void validarVendedorExistente(Usuario usuario) throws ValidacaoException {
        if (isEmpty(usuario.getVendedor().getId())) {
            throw new ValidacaoException("É preciso ter um vendedor para cadastrar um novo usuário");
        }
    }

    public Usuario validarTrocaDeSituacao(Usuario usuario) throws ValidacaoException {
        usuario = validarInativacao(usuario);
        usuario = validarAtivacao(usuario);
        return usuario;
    }

    public Usuario validarInativacao(Usuario usuario) throws ValidacaoException {
        if(!isNovoCadastro(usuario)) {
            Usuario usuarioAntigo = usuarioRepository.findById(usuario.getId())
                    .orElseThrow(() -> USUARIO_NAO_EXISTENTE_EXCEPTION);
            if (!usuario.getSituacao().equals(usuarioAntigo.getSituacao())
                && usuario.getSituacao().equals(INATIVO)) {
                    usuario.setSituacao(INATIVO);
                }
        }
        return  usuario;
    }

    public Usuario validarAtivacao(Usuario usuario) throws ValidacaoException {
        if(!isNovoCadastro(usuario)) {
            Usuario usuarioAntigo = usuarioRepository.findById(usuario.getId())
                    .orElseThrow(() -> USUARIO_NAO_EXISTENTE_EXCEPTION);

            List<Usuario> validaCliente = usuarioRepository.findByVendedorId(usuario.getVendedor().getId());

            if (!validaCliente.isEmpty()) {
                validaCliente
                        .stream()
                        .filter(usuarioCliente -> usuarioCliente.getSituacao().equals(ATIVO)
                                && usuario.getSituacao().equals(ATIVO)
                                && !usuarioCliente.getId().equals(usuario.getId()))
                        .forEach(cliente -> {
                            throw new ValidacaoException("Este vendedor já possui um "
                                    + "um usuário ATIVO, e não há possibilidade de um vendedor ter mais de um usuário"
                                    + " ATIVO. Inative o usuário atual para poder ativar este usuário para o vendedor.");
                        });
            }
            if (!usuario.getSituacao().equals(usuarioAntigo.getSituacao())
                    && usuario.getSituacao().equals(ATIVO)) {
                usuario.setSituacao(ATIVO);
            }
        }
        return  usuario;
    }


    public void validaEmailClienteESituacao(Usuario usuario) throws ValidacaoException {
        usuarioRepository.findByEmailAndSituacao(usuario.getEmail(), ATIVO)
            .ifPresent(usuarioValidar -> {
                if (isNovoCadastro(usuario) || !usuario.getId().equals(usuarioValidar.getId())) {
                    throw new ValidacaoException("Não é possível inserir o usuário pois o email "
                            + usuario.getEmail() + " já está cadastrado para um usuário ATIVO.");
                }
            });
    }

    public Usuario verificarDataUltimoAcesso(Usuario usuario) {
        if (!isNovoCadastro(usuario)) {
            usuarioRepository.findById(usuario.getId()).ifPresent(usuarioExistente -> {
                if (!isEmpty(usuarioExistente.getUltimoAcesso())) {
                    usuario.setUltimoAcesso(usuarioExistente.getUltimoAcesso());
                }
            });
        }
        return usuario;
    }

    public boolean isNovoCadastro(Usuario usuario) {
        return isEmpty(usuario.getId());
    }

    public void removerUsuario(Integer id) {
        if (!getIdsPermitidos().contains(id)) {
            throw new ValidacaoException("Você não tem permissão para remover esse usuário");
        }
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> USUARIO_NAO_EXISTENTE_EXCEPTION);
        List<RelatoriosPowerBi> relatorios = powerBiRepository.findByUsuario(usuario);
        Vendedor vendedor = vendedorRepository.findById(usuario.getVendedor().getId())
            .orElseThrow(() -> new ValidacaoException("Vendedor não identificado."));
        if (usuario.getSituacao().equals(ATIVO)) {
            throw new ValidacaoException("Não é possível remover esse usuário pois ele está ativo para o vendedor "
                    + vendedor.getNome() + ".");
        } else if (!relatorios.isEmpty()) {
            throw new ValidacaoException("Não é possível remover o usuário " + usuario.getNome() + " pois esse" +
                    " usuário está vinculado aos relatórios: " + getNomes(relatorios));
        } else {
            usuarioRepository.delete(usuario);
        }
    }


    public List<String> getNomes(List<RelatoriosPowerBi> relatorios) {
        ArrayList<String> relatoriosNomes =  new ArrayList<>();
        AtomicInteger index = new AtomicInteger(1);
        relatorios.forEach(
             relatorio -> {
                 relatoriosNomes.add(index + " - " + relatorio.getTitulo());
                 index.getAndIncrement();
             }
        );
        return relatoriosNomes;
    }

    public UsuarioAutenticadoDto getUsuarioLogado() {
        String email = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try{
            if (principal instanceof UserDetails) {
                email = (((UserDetails)principal).getUsername());
            }
        }
        catch (Exception e) {
            throw new ValidacaoException("Não há uma sessão de usuário ativa.");
        }
        Usuario usuario = usuarioRepository.findByEmailAndSituacao(email, ATIVO)
            .orElseThrow(() -> USUARIO_NAO_EXISTENTE_EXCEPTION);
        return UsuarioAutenticadoDto
            .builder()
            .id(usuario.getId())
            .email(usuario.getEmail())
            .nome(usuario.getNome())
            .permissao(usuario.getPermissoesUsuario())
            .build();
    }

    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        UsuarioAutenticadoDto usuarioLogado = getUsuarioLogado();
        if (usuarioLogado.isAdmin()) {
            usuarios = getUsuariosNivelAdmin(usuarios, usuarioLogado.getId());
        }
        return usuarios;
    }

    public List<Integer> getIdsPermitidos () {
        return buscarTodos()
            .stream()
            .map(Usuario::getId)
            .collect(toList());
    }

    public List<Usuario> getUsuariosNivelAdmin(List<Usuario> usuarios, Integer usuarioLogadoId) {
        return usuarios
                .stream()
                .filter(usuario -> usuario.getId().equals(usuarioLogadoId)
                    || !isEmpty(usuario.getUsuarioProprietario())
                    && usuario.getUsuarioProprietario().equals(usuarioLogadoId))
                .collect(toList());
    }

    public Usuario buscarUm(Integer id) {
        List<Integer> usuariosPermitidos = new ArrayList<>();
        buscarTodos().
            forEach(usuario -> {
                usuariosPermitidos.add(usuario.getId());
            });
        if (!usuariosPermitidos.contains(id)) {
            throw new ValidacaoException("Você não tem permissão para ver esse usuário.");
        }
        return usuarioRepository.findById(id)
            .orElseThrow(() -> USUARIO_NAO_EXISTENTE_EXCEPTION);
    }

    public List<Usuario> buscarAdministradores() {
        UsuarioAutenticadoDto usuarioLogado = getUsuarioLogado();
        List<Usuario> usuarios;
        PermissoesUsuario admin = permissoesUsuarioRepository.findById(2).get();
        PermissoesUsuario superAdmin = permissoesUsuarioRepository.findById(3).get();
        if (usuarioLogado.isSuperAdmin()) {
            usuarios = usuarioRepository.findByPermissoesUsuarioInAndSituacao(Arrays.asList(admin, superAdmin), ATIVO);
        } else {
            usuarios = usuarioRepository.findByPermissoesUsuarioAndSituacao(admin, ATIVO);
            usuarios = getUsuariosNivelAdmin(usuarios, usuarioLogado.getId());
        }
        return usuarios;
    }

    @Transactional
    public Usuario atualizarUltimoAcesso(Integer id) {
        if (!getIdsPermitidos().contains(id)) {
            throw new ValidacaoException("Você não tem permissão para atualizar o último acesso desse usuário");
        }
        usuarioRepository.atualizarUltimoAcessoUsuario(LocalDateTime.now(), id);
        return usuarioRepository.findById(id).orElseThrow(() -> USUARIO_NAO_EXISTENTE_EXCEPTION);
    }

    @Transactional
    public void alterarSenhaUsuario(UsuarioAlteracaoSenhaDto usuarioAlteracaoSenhaDto) {
        if (!getIdsPermitidos().contains(usuarioAlteracaoSenhaDto.getUsuarioId())) {
            throw new ValidacaoException("Você não tem permissão para atualizar a senha desse usuário");
        }
        usuarioRepository.atualizarSenha(usuarioAlteracaoSenhaDto.getNovaSenha(),
            usuarioAlteracaoSenhaDto.getUsuarioId());
    }

}
