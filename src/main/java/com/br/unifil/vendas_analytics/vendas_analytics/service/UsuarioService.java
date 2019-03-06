package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Cliente;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ClienteRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.INATIVO;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public void salvarUsuario(Usuario usuario) throws ValidationException {
        if (isNovoCadastro(usuario)) {
            Date date = null;
            Calendar calendar = Calendar.getInstance();
            usuario.setSituacao(ATIVO);
            usuario.setDataCadastro(date = calendar.getTime());
        }
        try {
            validaUsuario(usuario);
            usuario = validarTrocaDeSituacao(usuario);
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new ValidationException("Erro ao salvar usuário");
        }
    }

    public void validaUsuario(Usuario usuario) throws ValidationException {
        validarClienteExistente(usuario);
        if (isNovoCadastro(usuario) && !usuario.getSituacao().equals(ATIVO)) {
            throw new ValidationException("Não é possível cadastrar um usuário INATIVO.");
        }
        validaEmailClienteESituacao(usuario);
        validarTrocaDeEmail(usuario);
    }

    public void validarClienteExistente(Usuario usuario) throws ValidationException {
        if (isNovoCadastro(usuario)) {
            if (ObjectUtils.isEmpty(usuario.getCliente().getId())) {
                throw new ValidationException("É preciso ter um cliente para cadastrar um novo usuário");
            }
        }
    }

    public Usuario validarTrocaDeSituacao(Usuario usuario) throws ValidationException {
        usuario = validarInativacao(usuario);
        usuario = validarAtivacao(usuario);
        return usuario;
    }

    public Usuario validarInativacao(Usuario usuario) throws ValidationException {
        if(!isNovoCadastro(usuario)) {
            Usuario usuarioAntigo = usuarioRepository.findById(usuario.getId())
                    .orElseThrow(() -> new ValidationException("Usuário não existe"));
            if (!usuario.getSituacao().equals(usuarioAntigo.getSituacao())
                && usuario.getSituacao().equals(INATIVO)) {
                    usuario.setSituacao(INATIVO);
                }
        }
        return  usuario;
    }

    public Usuario validarAtivacao(Usuario usuario) throws ValidationException {
        if(!isNovoCadastro(usuario)) {
            Usuario usuarioAntigo = usuarioRepository.findById(usuario.getId())
                    .orElseThrow(() -> new ValidationException("Usuário não existe"));
            if (!usuario.getSituacao().equals(usuarioAntigo.getSituacao())
                    && usuario.getSituacao().equals(ATIVO)) {
                usuario.setSituacao(ATIVO);
            }
        }
        return  usuario;
    }


    public void validaEmailClienteESituacao(Usuario usuario) throws ValidationException {
        Optional<Usuario> usuarioValidar = usuarioRepository
                .findByEmailAndSituacao(usuario.getEmail(), ATIVO);
        if (usuarioValidar.isPresent()) {
            throw new ValidationException("Não é possível inserir o usuário pois o email "
                                        + usuario.getEmail() + " já está cadastrado.");
        }
    }

    public void validarTrocaDeEmail(Usuario usuario) throws ValidationException {
        if (!isNovoCadastro(usuario)) {
            Cliente cliente = clienteRepository.findById(usuario.getCliente().getId())
                    .orElseThrow(() -> new ValidationException("O cliente não está cadastrado"));
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
