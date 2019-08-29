package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.PermissoesUsuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Venda;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Vendedor;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendedorRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VendaRepository vendaRepository;

    private static final ValidacaoException VENDEDOR_SEM_PERMISSAO = new ValidacaoException
        ("Você não tem permissão para ver esse vendedor.");

    private static final String SENHA_AUTOMATICA = "alterar";

    @Transactional
    public void salvarVendedor(Vendedor vendedor) throws ValidacaoException {
        try {
            if (isNovoCadastro(vendedor)) {
                validarNovoVendedor(vendedor);
            }
            vendedorRepository.save(vendedor);
            criaUsuarioAoInserirVendedor(vendedor);
        } catch (Exception ex) {
            throw new ValidacaoException("Não foi possível salvar o vendedor.");
        }
    }

    @Transactional
    public void removerVendedorComUsuarioComVendasVinculadas(Integer id) {
        if (!getVendedoresPermitidos().contains(id)) {
            throw new ValidacaoException("Você não tem permissão para remover esse vendedor.");
        }
        Vendedor vendedor = vendedorRepository.findById(id)
            .orElseThrow(() -> new ValidacaoException("Não foi possível encontrar o vendedor."));
        Usuario usuario = usuarioRepository.findByVendedorIdAndSituacao(vendedor.getId(), ATIVO)
            .orElseThrow(() -> new ValidacaoException("Não há usuário ativo para o vendedor " + vendedor.getNome()
                + ", por favor, verifique se o vendedor possui usuários inativos, ative novamente e tente fazer a " +
                    "remoção do vendedor."));
        try {
            List<Venda> vendas = vendaRepository.findByVendedor(vendedor);
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

    public void criaUsuarioAoInserirVendedor(Vendedor vendedor) throws ValidacaoException {
        if (!hasUsuario(vendedor)) {
            Usuario usuario = Usuario
                    .builder()
                    .dataCadastro(LocalDateTime.now())
                    .email(vendedor.getEmail())
                    .nome(vendedor.getNome())
                    .senha(SENHA_AUTOMATICA)
                    .situacao(ATIVO)
                    .permissoesUsuario(PermissoesUsuario.builder().id(1).build())
                    .vendedor(vendedor)
                    .usuarioProprietario(usuarioService.getUsuarioLogado().getId())
                    .ultimoAcesso(null)
                    .build();
            usuarioService.salvarUsuario(usuario);
        }
    }

    public boolean hasUsuario(Vendedor vendedor) {
        AtomicReference<Boolean> hasUsuario = new AtomicReference<>();
        hasUsuario.set(false);
        usuarioRepository.findByVendedorId(vendedor.getId()).forEach(
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

    public void validarNovoVendedor(Vendedor vendedor) throws ValidacaoException {
        validarCpfCadastrado(vendedor);
        validarEmailCadastrado(vendedor);
    }

    public void validarCpfCadastrado(Vendedor vendedor) throws ValidacaoException {
        Optional<Vendedor> vendedorCpf = vendedorRepository.findByCpf(vendedor.getCpf());
        if (vendedorCpf.isPresent() && !vendedor.getId().equals(vendedorCpf.get().getId())) {
            throw new ValidacaoException("CPF já cadastrado");
        }
    }

    public void validarEmailCadastrado(Vendedor vendedor) throws ValidacaoException {
        Optional<Vendedor> vendedorEmail = vendedorRepository.findByEmail(vendedor.getEmail());
        if (vendedorEmail.isPresent() && !vendedor.getId().equals(vendedorEmail.get().getId())) {
            throw new ValidacaoException("Email já cadastrado");
        }
    }

    public boolean isNovoCadastro(Vendedor vendedor) {
        return ObjectUtils.isEmpty(vendedor.getId());
    }

    public List<Vendedor> buscarTodos() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        if (usuarioLogado.isUser()) {
            return Collections.singletonList(vendedorRepository.findByEmail(usuarioLogado.getEmail())
                .orElseThrow(() -> new ValidacaoException("Vendedor sem usuário ou com email inválido.")));
        } else if (usuarioLogado.isAdmin()) {
            return vendedorRepository.findByIdIn(getVendedoresPermitidos());
        } else {
            return vendedorRepository.findAll();
        }
    }

    public List<Integer> getVendedoresPermitidos() {
        List<Integer> vendedoresIds = new ArrayList<>();
        usuarioService.buscarTodos()
            .forEach(usuario -> vendedoresIds.add(usuario.getVendedor().getId()));
        return vendedoresIds;
    }

    public Vendedor buscarUm(Integer id) {
        Vendedor vendedor =  vendedorRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("O vendedor não existe."));
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        if (usuarioLogado.isUser()) {
            validarPermissaoVendedorUser(id, usuarioLogado);
        }
        if (!getVendedoresPermitidos().contains(id)) {
            throw VENDEDOR_SEM_PERMISSAO;
        }
        return vendedor;
    }

    public void validarPermissaoVendedorUser(Integer idBuscado, UsuarioAutenticadoDto usuarioLogado) {
        usuarioRepository.findById(usuarioLogado.getId()).ifPresent(
            usuario -> {
                if (!usuario.getVendedor().getId().equals(idBuscado)) {
                    throw VENDEDOR_SEM_PERMISSAO;
                }
            });
    }

}
