package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.*;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.DashboardRepository;
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
    private UsuarioService usuarioService;

    @GetMapping("/vendas-por-periodo")
    public List<VendasPorPeriodoDto> getAllVendasPorPeriodo() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return dashboardRepository.vendasPorPeriodo(usuarioService.getUsuarioLogado().getId(),
            usuarioLogado.isSuperAdmin());
    }

    @GetMapping("/card1/vendas-por-vendedor")
    public List<VendasVendedoresDashboardDto> getAllVendasPorVendedor() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return dashboardRepository.vendasPorVendedores(usuarioLogado.getId(), usuarioLogado.isSuperAdmin());
    }

    @GetMapping("/card2/vendas-por-produto")
    public List<VendasProdutosDashboardDto> getAllVendasPorProduto() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return dashboardRepository.vendasPorProdutos(usuarioLogado.getId(), usuarioLogado.isSuperAdmin());
    }

    @GetMapping("/card3/vendas-feitas")
    public List<VendasSituacoesDto> getAllVendasFeitas() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return dashboardRepository.vendasRealizadasDashboard(usuarioLogado.getId(), usuarioLogado.isSuperAdmin());
    }

    @GetMapping("/card4/vendas-rejeitadas")
    public List<VendasSituacoesDto> getAllVendasRejeitadas() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return dashboardRepository.vendasNaoRealizadasDashboard(usuarioLogado.getId(), usuarioLogado.isSuperAdmin());
    }

    @GetMapping("/vendas-analise-dashboard")
    public List<VendasAnaliseDashboardDto> getAllVendasDashboard() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return dashboardRepository.vendasAnaliseDashboard(usuarioLogado.getId(), usuarioLogado.isSuperAdmin());
    }

    @GetMapping("/cards-totais")
    public CardsDashboardDto getSomatorios() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return dashboardRepository.totalVendedores(usuarioLogado.getId(), usuarioLogado.isSuperAdmin()).get(0);
    }
}
