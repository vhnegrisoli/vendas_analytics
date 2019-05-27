package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Vendedor;
import com.br.unifil.vendas_analytics.vendas_analytics.model.PermissoesUsuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Venda;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendedorRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;

@Service
public class VendedorService {

    @Autowired
    VendedorRepository vendedorRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    VendaRepository vendaRepository;

    @Transactional
    public void salvarCliente(Vendedor vendedor) throws ValidacaoException {
        try {
            if (isNovoCadastro(vendedor)) {
                validarNovoCliente(vendedor);
            }
            vendedorRepository.save(vendedor);
            criaUsuarioAoInserirCliente(vendedor);
        } catch (Exception ex) {
            throw new ValidacaoException("Não foi possível salvar o vendedor.");
        }
    }

    @Transactional
    public void removerClienteComUsuarioComVendasVinculadas(Integer id) {
        Vendedor vendedor = vendedorRepository.findById(id)
            .orElseThrow(() -> new ValidacaoException("Não foi possível encontrar o vendedor."));
        Usuario usuario = usuarioRepository.findByClienteIdAndSituacao(vendedor.getId(), ATIVO)
            .orElseThrow(() -> new ValidacaoException("Não há usuário ativo para o vendedor " + vendedor.getNome()
                + ", por favor, verifique se o vendedor possui usuários inativos, ative novamente e tente fazer a " +
                    "remoção do vendedor."));
        try {
            List<Venda> vendas = vendaRepository.findByClientes(vendedor);
            if (!vendas.isEmpty()) {
                throw new ValidacaoException("O vendedor " + vendedor.getNome() + " não pode ser removido pois já " +
                        "possui vendas cadastradas em seu nome. Por favor, contate o administrador do " +
                        "sistema para a remoção.");
            }
            usuarioRepository.delete(usuario);
            vendedorRepository.delete(vendedor);
        } catch (Exception e){
            throw e;
        }
    }

    public void criaUsuarioAoInserirCliente(Vendedor vendedor) throws ValidacaoException {
        if (!hasUsuario(vendedor)) {
            Usuario usuario = Usuario
                    .builder()
                    .dataCadastro(LocalDateTime.now())
                    .email(vendedor.getEmail())
                    .nome(vendedor.getNome())
                    .senha(gerarSenha())
                    .situacao(ATIVO)
                    .permissoesUsuario(PermissoesUsuario.builder().id(1).build())
                    .cliente(vendedor)
                    .build();
            usuarioService.salvarUsuario(usuario);
        }
    }

    public boolean hasUsuario(Vendedor vendedor) {
        AtomicReference<Boolean> hasUsuario = new AtomicReference<>();
        hasUsuario.set(false);
        usuarioRepository.findByClienteId(vendedor.getId()).forEach(
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

    public void validarNovoCliente(Vendedor vendedor) throws ValidacaoException {
        validarCpfCadastrado(vendedor);
        validarEmailCadastrado(vendedor);
    }

    public void validarCpfCadastrado(Vendedor vendedor) throws ValidacaoException {
        Optional<Vendedor> clienteCpf = vendedorRepository.findByCpf(vendedor.getCpf());
        if (clienteCpf.isPresent() && !vendedor.getId().equals(clienteCpf.get().getId())) {
            throw new ValidacaoException("CPF já cadastrado");
        }
    }

    public void validarEmailCadastrado(Vendedor vendedor) throws ValidacaoException {
        Optional<Vendedor> clienteEmail = vendedorRepository.findByEmail(vendedor.getEmail());
        if (clienteEmail.isPresent() && !vendedor.getId().equals(clienteEmail.get().getId())) {
            throw new ValidacaoException("Email já cadastrado");
        }
    }

    public boolean isNovoCadastro(Vendedor vendedor) {
        return ObjectUtils.isEmpty(vendedor.getId());
    }
}
