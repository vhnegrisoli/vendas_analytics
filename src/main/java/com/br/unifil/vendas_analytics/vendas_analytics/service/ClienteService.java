package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Cliente;
import com.br.unifil.vendas_analytics.vendas_analytics.model.PermissoesUsuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Venda;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ClienteRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    VendaRepository vendaRepository;

    @Transactional
    public void salvarCliente(Cliente cliente) throws ValidacaoException {
        try {
            if (isNovoCadastro(cliente)) {
                validarNovoCliente(cliente);
            }
            clienteRepository.save(cliente);
            criaUsuarioAoInserirCliente(cliente);
        } catch (Exception ex) {
            throw new ValidacaoException("Não foi possível salvar o vendedor.");
        }
    }

    @Transactional
    public void removerClienteComUsuarioComVendasVinculadas(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ValidacaoException("Não foi possível encontrar o vendedor."));
        Usuario usuario = usuarioRepository.findByClienteIdAndSituacao(cliente.getId(), ATIVO)
            .orElseThrow(() -> new ValidacaoException("Não há usuário ativo para o cliente " + cliente.getNome()
                + ", por favor, verifique se o cliente possui usuários inativos, ative novamente e tente fazer a " +
                    "remoção do cliente."));
        try {
            List<Venda> vendas = vendaRepository.findByClientes(cliente);
            if (!vendas.isEmpty()) {
                throw new ValidacaoException("O vendedor " + cliente.getNome() + " não pode ser removido pois já " +
                        "possui vendas cadastradas em seu nome. Por favor, contate o administrador do " +
                        "sistema para a remoção.");
            }
            usuarioRepository.delete(usuario);
            clienteRepository.delete(cliente);
        } catch (Exception e){
            throw e;
        }
    }

    public void criaUsuarioAoInserirCliente(Cliente cliente) throws ValidacaoException {
        if (!hasUsuario(cliente)) {
            Usuario usuario = Usuario
                    .builder()
                    .dataCadastro(LocalDateTime.now())
                    .email(cliente.getEmail())
                    .nome(cliente.getNome())
                    .senha(gerarSenha())
                    .situacao(ATIVO)
                    .permissoesUsuario(PermissoesUsuario.builder().id(1).build())
                    .cliente(cliente)
                    .build();
            usuarioService.salvarUsuario(usuario);
        }
    }

    public boolean hasUsuario(Cliente cliente) {
        AtomicReference<Boolean> hasUsuario = new AtomicReference<>();
        hasUsuario.set(false);
        usuarioRepository.findByClienteId(cliente.getId()).forEach(
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

    public void validarNovoCliente(Cliente cliente) throws ValidacaoException {
        validarCpfCadastrado(cliente);
        validarEmailCadastrado(cliente);
    }

    public void validarCpfCadastrado(Cliente cliente) throws ValidacaoException {
        Optional<Cliente> clienteCpf = clienteRepository.findByCpf(cliente.getCpf());
        if (clienteCpf.isPresent() && !cliente.getId().equals(clienteCpf.get().getId())) {
            throw new ValidacaoException("CPF já cadastrado");
        }
    }

    public void validarEmailCadastrado(Cliente cliente) throws ValidacaoException {
        Optional<Cliente> clienteEmail = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteEmail.isPresent() && !cliente.getId().equals(clienteEmail.get().getId())) {
            throw new ValidacaoException("Email já cadastrado");
        }
    }

    public boolean isNovoCadastro(Cliente cliente) {
        return ObjectUtils.isEmpty(cliente.getId());
    }
}
