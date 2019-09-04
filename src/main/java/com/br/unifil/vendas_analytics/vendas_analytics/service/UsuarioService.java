package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.UsuarioAlteracaoSenhaDto;
import com.br.unifil.vendas_analytics.vendas_analytics.enums.PermissoesUsuarioEnum;
import com.br.unifil.vendas_analytics.vendas_analytics.model.PermissoesUsuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.RelatoriosPowerBi;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.PermissoesUsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.PowerBiRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage.UsuarioExceptionMessage.*;
import static com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage.VendedorExceptionMessage.VENDEDOR_NAO_ENCONTRADO;
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

    private static final List<PermissoesUsuarioEnum> PERMISSOES_ADMIN = Arrays.asList(ADMIN, SUPER_ADMIN);

    private static final Integer INDEX_ADMIN = 2;

    private static final Integer INDEX_SUPER_ADMIN = 3;

    public void salvarUsuario(Usuario usuario) {
        if (usuario.isNovoCadastro()) {
            usuario.setSituacao(ATIVO);
        }
        usuario.setDataCadastro(LocalDateTime.now());
        validaUsuario(usuario);
        usuario = validarTrocaDeSituacao(usuario);
        usuario = verificarDataUltimoAcesso(usuario);
        usuarioRepository.save(usuario);
    }

    public void validaUsuario(Usuario usuario) {
        validarVendedorExistente(usuario);
        validaEmailVendedorESituacao(usuario);
    }

    public void validarVendedorExistente(Usuario usuario) {
        if (isEmpty(usuario.getVendedor().getId())) {
            throw USUARIO_SEM_VENDEDOR.getException();
        }
    }

    public Usuario validarTrocaDeSituacao(Usuario usuario) {
        usuario = validarInativacao(usuario);
        usuario = validarAtivacao(usuario);
        return usuario;
    }

    public Usuario validarInativacao(Usuario usuario) {
        if (!usuario.isNovoCadastro()) {
            Usuario usuarioAntigo = usuarioRepository.findById(usuario.getId())
                .orElseThrow(USUARIO_NAO_ENCONTRADO::getException);
            if (!usuario.getSituacao().equals(usuarioAntigo.getSituacao())
                && usuario.getSituacao().equals(INATIVO)) {
                usuario.setSituacao(INATIVO);
            }
        }
        return  usuario;
    }

    public Usuario validarAtivacao(Usuario usuario) {
        if (!usuario.isNovoCadastro()) {
            Usuario usuarioAntigo = usuarioRepository.findById(usuario.getId())
                .orElseThrow(USUARIO_NAO_ENCONTRADO::getException);
            List<Usuario> usuarios = usuarioRepository.findByVendedorId(usuario.getVendedor().getId());
            if (!usuarios.isEmpty()) {
                usuarios.forEach(usuarioVendedor -> {
                    if (usuario.getVendedor().getId().equals(usuarioVendedor.getId())
                        && !usuario.getId().equals(usuarioVendedor.getId())) {
                        throw USUARIO_COM_VENDEDOR.getException();
                    }
                });
            }
            if (!usuario.getSituacao().equals(usuarioAntigo.getSituacao()) && usuario.getSituacao().equals(ATIVO)) {
                usuario.setSituacao(ATIVO);
            }
        }
        return  usuario;
    }

    public void validaEmailVendedorESituacao(Usuario usuario) {
        Usuario usuarioValidar = usuarioRepository.findByEmail(usuario.getEmail())
            .orElseThrow(USUARIO_NAO_ENCONTRADO::getException);
        if (usuario.isNovoCadastro() || !usuario.getId().equals(usuarioValidar.getId())) {
            throw USUARIO_EMAIL_JA_CADASTRADO.getException();
        }
    }

    public Usuario verificarDataUltimoAcesso(Usuario usuario) {
        if (!usuario.isNovoCadastro()) {
            usuarioRepository.findById(usuario.getId()).ifPresent(usuarioExistente -> {
                if (!isEmpty(usuarioExistente.getUltimoAcesso())) {
                    usuario.setUltimoAcesso(usuarioExistente.getUltimoAcesso());
                }
            });
        }
        return usuario;
    }

    public void removerUsuario(Integer id) {
        if (!getIdsPermitidos().contains(id)) {
            throw USUARIO_SEM_PERMISSAO_REMOVER.getException();
        }
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(USUARIO_NAO_ENCONTRADO::getException);
        List<RelatoriosPowerBi> relatorios = powerBiRepository.findByUsuario(usuario);
        vendedorRepository.findById(usuario.getVendedor().getId()).orElseThrow((VENDEDOR_NAO_ENCONTRADO::getException));
        if (usuario.getSituacao().equals(ATIVO)) {
            throw USUARIO_ATIVO.getException();
        } else if (!relatorios.isEmpty()) {
            throw USUARIO_COM_RELATORIOS.getException();
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
        try {
            if (principal instanceof UserDetails) {
                email = (((UserDetails)principal).getUsername());
            }
        } catch (Exception ex) {
            throw USUARIO_SEM_SESSAO.getException();
        }
        Usuario usuario = usuarioRepository.findByEmailAndSituacao(email, ATIVO)
            .orElseThrow(USUARIO_NAO_ENCONTRADO::getException);
        return new UsuarioAutenticadoDto(usuario.getId(), usuario.getNome(), usuario.getEmail(),
            usuario.getPermissoesUsuario());
    }

    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        UsuarioAutenticadoDto usuarioLogado = getUsuarioLogado();
        if (usuarioLogado.isAdmin()) {
            usuarios = getUsuariosNivelAdmin(usuarios, usuarioLogado.getId());
        }
        return usuarios;
    }

    public List<Integer> getIdsPermitidos() {
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
        buscarTodos()
            .forEach(usuario -> usuariosPermitidos.add(usuario.getId()));
        if (!usuariosPermitidos.contains(id)) {
            throw USUARIO_SEM_PERMISSAO_VISUALIZAR.getException();
        }
        return usuarioRepository.findById(id)
            .orElseThrow(USUARIO_NAO_ENCONTRADO::getException);
    }

    public List<Usuario> buscarAdministradores() {
        UsuarioAutenticadoDto usuarioLogado = getUsuarioLogado();
        List<Usuario> usuarios;
        PermissoesUsuario admin = permissoesUsuarioRepository.findById(INDEX_ADMIN)
            .orElseThrow(PERMISSAO_NAO_ENCONTRADA::getException);
        PermissoesUsuario superAdmin = permissoesUsuarioRepository.findById(INDEX_SUPER_ADMIN)
            .orElseThrow(PERMISSAO_NAO_ENCONTRADA::getException);
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
            throw USUARIO_SEM_PERMISSAO_ATUALIZAR_ULTIMO_ACESSO.getException();
        }
        usuarioRepository.atualizarUltimoAcessoUsuario(LocalDateTime.now(), id);
        return usuarioRepository.findById(id).orElseThrow(USUARIO_NAO_ENCONTRADO::getException);
    }

    @Transactional
    public void alterarSenhaUsuario(UsuarioAlteracaoSenhaDto usuarioAlteracaoSenhaDto) {
        if (!getIdsPermitidos().contains(usuarioAlteracaoSenhaDto.getUsuarioId())) {
            throw USUARIO_SEM_PERMISSAO_ATUALIZAR_SENHA.getException();
        }
        usuarioRepository.atualizarSenha(usuarioAlteracaoSenhaDto.getNovaSenha(),
            usuarioAlteracaoSenhaDto.getUsuarioId());
    }
}