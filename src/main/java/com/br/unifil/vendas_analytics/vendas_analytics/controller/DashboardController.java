package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.CardsDashboardDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.VendasPorPeriodoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard.*;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendedorRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ProdutoRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.RelatoriosRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.repository_relatorios_dashboard.*;
import com.br.unifil.vendas_analytics.vendas_analytics.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum.APROVADA;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum.FECHADA;

@CrossOrigin
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private Vendas_Por_PeriodoRepository vendas_por_periodoRepository;

    @Autowired
    private VendedorRepository vendedorRepository;


    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RelatoriosRepository relatoriosRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private Vendas_Por_VendedorRepository vendas_por_vendedorRepository;

    @Autowired
    private Vendas_Por_ProdutoRepository vendas_por_produtoRepository;

    @Autowired
    private Vendas_FeitasRepository vendas_feitasRepository;

    @Autowired
    private Vendas_RejeitadasRepository vendas_rejeitadasRepository;

    @Autowired
    private Vendas_DashboardRepository vendas__dashboardRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/vendas-por-periodo")
    public List<VendasPorPeriodoDto> getAllVendasPorPeriodo() {
        return relatoriosRepository.vendasPorPeriodo(usuarioService.getUsuarioLogado().getId());
    }

    @GetMapping("/card1/vendas-por-vendedor")
    public List<vendas_por_vendedor> getAllVendasPorVendedor() {
        return vendas_por_vendedorRepository.findAll();
    }

    @GetMapping("/card2/vendas-por-produto")
    public List<vendas_por_produto> getAllVendasPorProduto() {
        return vendas_por_produtoRepository.findAll();
    }

    @GetMapping("/card3/vendas-feitas")
    public List<vendas_feitas> getAllVendasFeitas() {
        return vendas_feitasRepository.findAll();
    }

    @GetMapping("/card4/vendas-rejeitadas")
    public List<vendas_rejeitadas> getAllVendasRejeitadas() {
        return vendas_rejeitadasRepository.findAll();
    }

    @GetMapping("/vendas-analise-dashboard")
    public List<Vendas_Dashboard> getAllVendasDashboard() {
        return vendas__dashboardRepository.findAll();
    }

    @GetMapping("/cards-totais")
    public CardsDashboardDto getSomatorios() {
        long qtdClientes = vendedorRepository.count();
        long qtdProdutos = produtoRepository.count();
        long qtdVendasRealizadas = vendaRepository.countBySituacaoAndAprovacao(FECHADA, APROVADA);
        long qtdVendasNaoRealizadas = vendaRepository.countByAprovacaoNot(APROVADA);
        return CardsDashboardDto
                .builder()
                .qtdClientes(qtdClientes)
                .qtdProdutos(qtdProdutos)
                .qtdVendasRealizadas(qtdVendasRealizadas)
                .qtdVendasNaoRealizadas(qtdVendasNaoRealizadas)
                .build();
    }
}
