package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.CardsDashboardDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.VendasPorPeriodoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard.*;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.*;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.repository_relatorios_dashboard.*;
import com.br.unifil.vendas_analytics.vendas_analytics.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardRepository dashboardRepository;

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
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return dashboardRepository.vendasPorPeriodo(usuarioService.getUsuarioLogado().getId(),
            usuarioLogado.isSuperAdmin());
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
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return dashboardRepository.totalVendedores(usuarioLogado.getId(), usuarioLogado.isSuperAdmin()).get(0);
    }
}
