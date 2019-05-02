package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Cliente;
import com.br.unifil.vendas_analytics.vendas_analytics.model.RelatoriosPowerBi;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ClienteRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.PowerBiRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.INATIVO;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PowerBiRepository powerBiRepository;

    private final ValidacaoException USUARIO_NAO_EXISTENTE_EXCEPTION = new ValidacaoException("O usuário não existe");

    public void salvarUsuario(Usuario usuario) throws ValidacaoException {
        if (isNovoCadastro(usuario)) {
            usuario.setSituacao(ATIVO);
            usuario.setDataCadastro(LocalDateTime.now());
        }
        validaUsuario(usuario);
        usuario = validarTrocaDeSituacao(usuario);
        usuarioRepository.save(usuario);
    }

    public void validaUsuario(Usuario usuario) throws ValidacaoException {
        validarClienteExistente(usuario);
        validaEmailClienteESituacao(usuario);
    }

    public void validarClienteExistente(Usuario usuario) throws ValidacaoException {
        if (isNovoCadastro(usuario)) {
            if (ObjectUtils.isEmpty(usuario.getCliente().getId())) {
                throw new ValidacaoException("É preciso ter um cliente para cadastrar um novo usuário");
            }
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
            if (usuarioAntigo.getCliente().getId().equals(usuario.getCliente().getId())
                && !usuarioAntigo.getId().equals(usuario.getId())
                && usuarioAntigo.getSituacao().equals(ATIVO) && usuario.getSituacao().equals(ATIVO)) {
                throw new ValidacaoException("O cliente " + usuario.getCliente().getNome() + " já possui um usuário " +
                    "ATIVO, e não há possibilidade de um cliente ter mais de um usuário ATIVO. Inative o usuário" +
                        " atual para poder ativar este usuário para o cliente " + usuario.getCliente().getNome());
            }
            if (!usuario.getSituacao().equals(usuarioAntigo.getSituacao())
                    && usuario.getSituacao().equals(ATIVO)) {
                usuario.setSituacao(ATIVO);
            }
        }
        return  usuario;
    }


    public void validaEmailClienteESituacao(Usuario usuario) throws ValidacaoException {
        Optional<Usuario> usuarioValidar = usuarioRepository
                .findByEmailAndSituacao(usuario.getEmail(), ATIVO);
        if (usuarioValidar.isPresent() && !usuario.getId().equals(usuarioValidar.get().getId())) {
            throw new ValidacaoException("Não é possível inserir o usuário pois o email "
                                        + usuario.getEmail() + " já está cadastrado.");
        }
    }

    public boolean isNovoCadastro(Usuario usuario) {
        return ObjectUtils.isEmpty(usuario.getId());
    }

    public void removerUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> USUARIO_NAO_EXISTENTE_EXCEPTION);

        List<RelatoriosPowerBi> relatorios = powerBiRepository.findByUsuario(usuario);

        if (usuario.getSituacao().equals(ATIVO)) {
            throw new ValidacaoException("Não é possível remover esse usuário pois ele está ativo para o cliente "
                    + usuario.getCliente().getNome() + ".");
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

}
