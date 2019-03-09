package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Cliente;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ClienteRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.xml.bind.ValidationException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;


    public void salvarCliente(Cliente cliente) throws ValidationException {
        try {
            if (ObjectUtils.isEmpty(cliente.getId())) {
                validarNovoCliente(cliente);
            }
            clienteRepository.save(cliente);
            criaUsuarioAoInserirCliente(cliente);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void criaUsuarioAoInserirCliente(Cliente cliente) throws ValidationException {
        if (!hasUsuario(cliente)) {
            Calendar calendar = Calendar.getInstance();
            Date date = null;
            Usuario usuario = Usuario
                    .builder()
                    .dataCadastro(date = calendar.getTime())
                    .email(cliente.getEmail())
                    .nome(cliente.getNome())
                    .senha(gerarSenha())
                    .situacao(UsuarioSituacao.ATIVO)
                    .cliente(cliente)
                    .build();
            usuarioService.salvarUsuario(usuario);
        }
    }

    public boolean hasUsuario(Cliente cliente) {
        AtomicReference<Boolean> hasUsuario = new AtomicReference<>();
        hasUsuario.set(false);
        usuarioRepository.findByClienteId(cliente.getId()).ifPresent(
                usuario -> {
                    hasUsuario.set(true);
                }
        );
        return hasUsuario.get();
    }

    public String gerarSenha() {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 45)
                .build();
        return pwdGenerator.generate(10);
    }

    public void validarNovoCliente(Cliente cliente) throws ValidationException {
        validarCpfCadastrado(cliente);
        validarEmailCadastrado(cliente);
    }

    public void validarCpfCadastrado(Cliente cliente) throws ValidationException {
        Optional<Cliente> clienteCpf = clienteRepository.findByCpf(cliente.getCpf());
        if (clienteCpf.isPresent()) {
            throw new ValidationException("CPF já cadastrado");
        }
    }

    public void validarEmailCadastrado(Cliente cliente) throws ValidationException {
        Optional<Cliente> clienteEmail = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteEmail.isPresent()) {
            throw new ValidationException("Email já cadastrado");
        }
    }
}
