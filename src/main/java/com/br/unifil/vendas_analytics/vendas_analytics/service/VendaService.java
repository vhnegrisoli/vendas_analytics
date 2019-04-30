package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.ProdutosDaVendaDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.*;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ClienteRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ProdutoVendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum.*;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum.ABERTA;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum.FECHADA;
import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoVendaRepository produtoVendaRepository;

    private final ValidacaoException VENDA_NAO_ENCONTRADA_EXCEPTION = new ValidacaoException("Venda não existente");

    public void save(Venda venda) {
        List<ProdutoVenda> produtos = venda.getProdutos();
        venda = insereData(venda);
        venda = validaVendaAguardandoAprovacao(venda);
        Venda vendaCadastrar = venda;
        validarProdutosEClientesNulos(venda);
        vendaCadastrar.setProdutos(null);
        validarClienteComUsuarioAtivo(venda.getClientes());
        vendaRepository.save(vendaCadastrar);
        saveVendaProduto(vendaCadastrar, produtos);
    }

    public void saveVendaProduto(Venda vendaCadastrar, List<ProdutoVenda> produtos) {
        AtomicReference<Integer> id = new AtomicReference<>(null);
        id.set(vendaCadastrar.getId());
        produtos.forEach(
                produto -> {
                    ProdutoVendaId pk = new ProdutoVendaId();
                    pk.setProdutoId(produto.getId().getProdutoId());
                    pk.setVendaId(id.get());
                    produto.setId(pk);
                    produtoVendaRepository.save(produto);
                }
        );
    }

    public Venda insereData(Venda venda) {
        venda.setDataCompra(null);
        LocalDate date = LocalDate.now();
        String mes = date.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt"));
        LocalDateTime dataCompra = LocalDateTime.now();
        venda.setDataCompra(dataCompra);
        venda.setMesCompra(mes);
        return venda;
    }


    public Venda validaVendaAguardandoAprovacao(Venda venda) {
        if (isNovaVenda(venda)) {
            venda.setAprovacao(AGUARDANDO_APROVACAO);
            venda.setSituacao(ABERTA);
        }
        return venda;
    }

    public void validarProdutosEClientesNulos(Venda venda) {
        if (venda.getProdutos().isEmpty()) {
            throw new ValidacaoException("Você deve cadastrar produtos no carrinho de compra para tratar uma venda.");
        }
        if (ObjectUtils.isEmpty(venda.getClientes())) {
            throw new ValidacaoException("Você deve selecionar cliente para realizar a venda.");
        }
    }


    @Transactional
    public void aprovarVenda(int id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> VENDA_NAO_ENCONTRADA_EXCEPTION);
        if (!isNovaVenda(venda)) {
            if (venda.getAprovacao().equals(AGUARDANDO_APROVACAO)) {
                venda.setAprovacao(APROVADA);
                venda.setSituacao(FECHADA);
            }
        }
        vendaRepository.save(venda);
    }

    @Transactional
    public void rejeitarVenda(int id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> VENDA_NAO_ENCONTRADA_EXCEPTION);
        if (!isNovaVenda(venda)) {
            if (venda.getAprovacao().equals(AGUARDANDO_APROVACAO)
                    || venda.getAprovacao().equals(APROVADA)) {
                venda.setAprovacao(REJEITADA);
                venda.setSituacao(FECHADA);
            }
        }
        vendaRepository.save(venda);
    }

    public void validarClienteComUsuarioAtivo(Cliente cliente) {
        usuarioRepository.findByClienteIdAndSituacao(cliente.getId(), ATIVO)
                .orElseThrow(() -> new ValidacaoException("Não existe um usuário ativo para este cliente."));
    }

    public boolean isNovaVenda(Venda venda) {
        return ObjectUtils.isEmpty(venda.getId());
    }

    public List<ProdutosDaVendaDto> produtosVenda(int id) {
        List<ProdutosDaVendaDto> produtos = new ArrayList();
        List<Object[]> objetosProdutos = vendaRepository.findAllProdutosDaVendaByVendaId(id);
        for (Object[] itemProduto : objetosProdutos) {
            ProdutosDaVendaDto produtoInserir = new ProdutosDaVendaDto();
            produtoInserir.setId((Integer) itemProduto[0]);
            produtoInserir.setProduto((String) itemProduto[1]);
            produtoInserir.setDescricao((String) itemProduto[2]);
            produtoInserir.setFornecedor((String) itemProduto[3]);
            produtoInserir.setCategoria((String) itemProduto[4]);
            produtoInserir.setPreco((Double) itemProduto[5]);
            produtoInserir.setQuantidade((Integer) itemProduto[6]);
            produtos.add(produtoInserir);
        }
        return produtos;
    }

}
