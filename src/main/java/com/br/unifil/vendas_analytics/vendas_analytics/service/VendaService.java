package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.HistoricoVendaDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.ProdutosDaVendaDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.*;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.*;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

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

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoVendaRepository produtoVendaRepository;

    @Autowired
    private RelatoriosRepository relatoriosRepository;

    private final ValidacaoException VENDA_NAO_ENCONTRADA_EXCEPTION = new ValidacaoException("Venda não existente");

    public void save(Venda venda) {
        List<ProdutoVenda> produtos = venda.getProdutos();
        venda = insereData(venda);
        venda = validaVendaAguardandoAprovacao(venda);
        Venda vendaCadastrar = venda;
        validarProdutosEVendedoresNulos(venda);
        vendaCadastrar.setProdutos(null);
        validarVendedorComUsuarioAtivo(venda.getVendedor());
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

    public void validarProdutosEVendedoresNulos(Venda venda) {
        if (venda.getProdutos().isEmpty()) {
            throw new ValidacaoException("Você deve cadastrar produtos no carrinho de compra para tratar uma venda.");
        }
        if (ObjectUtils.isEmpty(venda.getVendedor())) {
            throw new ValidacaoException("Você deve selecionar o vendedor para realizar a venda.");
        }
    }

    @Transactional
    public void aprovarVenda(int id) {
        Integer idPermitido = getIdVendaPermitida(id);
        Venda venda = vendaRepository.findById(idPermitido)
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
        Integer idPermitido = getIdVendaPermitida(id);
        Venda venda = vendaRepository.findById(idPermitido)
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

    public void validarVendedorComUsuarioAtivo(Vendedor vendedor) {
        usuarioRepository.findByVendedorIdAndSituacao(vendedor.getId(), ATIVO)
                .orElseThrow(() -> new ValidacaoException("Não existe um usuário ativo para este vendedor."));
    }

    public boolean isNovaVenda(Venda venda) {
        return ObjectUtils.isEmpty(venda.getId());
    }

    public List<Venda> buscarTodas() {
        List<Integer> vendedoresId = new ArrayList<>();
        vendedorService
            .buscarTodos()
            .forEach(vendedor -> {
                vendedoresId.add(vendedor.getId());
            });
        return vendaRepository.findByVendedorIdIn(vendedoresId);
    }

    public Venda buscarUma(Integer id) {
        List<Integer> vendedoresId = new ArrayList<>();
        List<Integer> vendasId = new ArrayList<>();
        vendedorService
            .buscarTodos()
            .forEach(venda -> {
                vendedoresId.add(venda.getId());
            });
        vendaRepository.findByVendedorIdIn(vendedoresId)
            .forEach(venda -> {
                vendasId.add(venda.getId());
            });
        if (!vendasId.contains(id)) {
            throw new ValidacaoException("Você não tem permissão para ver esta venda.");
        }
        return vendaRepository.findById(id)
            .orElseThrow(() -> VENDA_NAO_ENCONTRADA_EXCEPTION);
    }

    public List<ProdutosDaVendaDto> getProdutosDaVenda(Integer id) {
        Integer idPermitido = getIdVendaPermitida(id);
        return relatoriosRepository.findAllProdutosDaVendaByVendaId(idPermitido);
    }

    public Integer getIdVendaPermitida(Integer id) {
        return buscarUma(id).getId();
    }

}
