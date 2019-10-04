package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.PermissoesUsuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Venda;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Vendedor;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendedorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage.VendedorExceptionMessage.*;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Slf4j
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VendaRepository vendaRepository;

    private static final String SENHA_AUTOMATICA = "alterar";

    @Transactional
    public void salvarVendedor(Vendedor vendedor) {
        validarDadosVendedor(vendedor);
        vendedorRepository.save(vendedor);
        criaUsuarioAoInserirVendedor(vendedor);
    }

    @Transactional
    public void validarVendedorComUsuarioComVendasVinculadas(Integer id) {
        if (!getVendedoresPermitidos().contains(id)) {
            throw VENDEDOR_SEM_PERMISSAO_REMOVER.getException();
        }
        Vendedor vendedor = vendedorRepository.findById(id)
            .orElseThrow(VENDEDOR_NAO_ENCONTRADO::getException);
        Usuario usuario = usuarioRepository.findByVendedorIdAndSituacao(vendedor.getId(), ATIVO)
            .orElseThrow(VENDEDOR_SEM_PERMISSAO_VISUALIZAR::getException);
        removerVendedor(usuario, vendedor);
    }

    private void removerVendedor(Usuario usuario, Vendedor vendedor) {
        List<Venda> vendas = vendaRepository.findByVendedor(vendedor);
        if (!vendas.isEmpty()) {
            throw VENDEDOR_VENDAS_CADASTRADAS.getException();
        }
        usuarioRepository.delete(usuario);
        vendedorRepository.delete(vendedor);
    }

    public void criaUsuarioAoInserirVendedor(Vendedor vendedor) {
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

    public void validarDadosVendedor(Vendedor vendedor) {
        validarDataNascimento(vendedor);
        validarCpfCadastrado(vendedor);
        validarEmailCadastrado(vendedor);
    }

    private void validarDataNascimento(Vendedor vendedor) {
        if (vendedor.isNovoCadastro()) {
            if (isEmpty(vendedor.getDataNascimento())) {
                throw VENDEDOR_SEM_DATA_NASCIMENTO.getException();
            }
        } else {
            Vendedor vendedorExistente = vendedorRepository.findById(vendedor.getId())
                .orElseThrow(VENDEDOR_NAO_ENCONTRADO::getException);
            if (isEmpty(vendedor.getDataNascimento())) {
                vendedor.setDataNascimento(vendedorExistente.getDataNascimento());
            }
        }
    }

    private void validarCpfCadastrado(Vendedor vendedor) {
        if (vendedor.isNovoCadastro() && vendedorRepository.existsByCpf(vendedor.getCpf())) {
            throw VENDEDOR_CPF_JA_CADASTRADO.getException();
        }
        vendedorRepository.findByCpf(vendedor.getCpf())
            .ifPresent(vendedorCpf -> {
                if (!vendedor.getId().equals(vendedorCpf.getId())) {
                    throw VENDEDOR_CPF_JA_CADASTRADO.getException();
                }
            });
    }

    private void validarEmailCadastrado(Vendedor vendedor) {
        if (vendedor.isNovoCadastro() && vendedorRepository.existsByEmail(vendedor.getEmail())) {
            throw VENDEDOR_EMAIL_JA_CADASTRADO.getException();
        }
        vendedorRepository.findByEmail(vendedor.getEmail())
            .ifPresent(vendedorEmail -> {
                if (!vendedor.getId().equals(vendedorEmail.getId())) {
                    throw VENDEDOR_EMAIL_JA_CADASTRADO.getException();
                }
            });
    }

    public List<Vendedor> buscarTodos() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        if (usuarioLogado.isUser()) {
            return Collections.singletonList(vendedorRepository.findByEmail(usuarioLogado.getEmail())
                .orElseThrow(VENDEDOR_EMAIL_INVALIDO::getException));
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
            .orElseThrow(VENDEDOR_NAO_ENCONTRADO::getException);
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        if (usuarioLogado.isUser()) {
            validarPermissaoVendedorUser(id, usuarioLogado);
        }
        if (!getVendedoresPermitidos().contains(id)) {
            throw VENDEDOR_SEM_PERMISSAO_VISUALIZAR.getException();
        }
        return vendedor;
    }

    public void validarPermissaoVendedorUser(Integer idBuscado, UsuarioAutenticadoDto usuarioLogado) {
        usuarioRepository.findById(usuarioLogado.getId()).ifPresent(
            usuario -> {
                if (!usuario.getVendedor().getId().equals(idBuscado)) {
                    throw VENDEDOR_SEM_PERMISSAO_VISUALIZAR.getException();
                }
            });
    }
}