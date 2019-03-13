package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Cliente;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ClienteRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.time.LocalDateTime;
import java.util.Optional;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.INATIVO;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public void salvarUsuario(Usuario usuario) throws ValidacaoException {
        if (isNovoCadastro(usuario)) {
            usuario.setSituacao(ATIVO);
            usuario.setDataCadastro(LocalDateTime.now());
        }
        try {
            validaUsuario(usuario);
            usuario = validarTrocaDeSituacao(usuario);
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new ValidacaoException("Erro ao salvar usuário");
        }
    }

    public void validaUsuario(Usuario usuario) throws ValidacaoException {
        validarClienteExistente(usuario);
        validaEmailClienteESituacao(usuario);
        validarTrocaDeEmail(usuario);
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
                    .orElseThrow(() -> new ValidacaoException("Usuário não existe"));
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
                    .orElseThrow(() -> new ValidacaoException("Usuário não existe"));
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
        if (usuarioValidar.isPresent()) {
            throw new ValidacaoException("Não é possível inserir o usuário pois o email "
                                        + usuario.getEmail() + " já está cadastrado.");
        }
    }

    public void validarTrocaDeEmail(Usuario usuario) throws ValidacaoException {
        if (!isNovoCadastro(usuario)) {
            Cliente cliente = clienteRepository.findById(usuario.getCliente().getId())
                    .orElseThrow(() -> new ValidacaoException("O cliente não está cadastrado"));
            if (!usuario.getEmail().equals(cliente.getEmail())) {
                cliente.setEmail(usuario.getEmail());
                clienteRepository.save(cliente);
            }
        }
    }

    public boolean isNovoCadastro(Usuario usuario) {
        return ObjectUtils.isEmpty(usuario.getId());
    }

}
