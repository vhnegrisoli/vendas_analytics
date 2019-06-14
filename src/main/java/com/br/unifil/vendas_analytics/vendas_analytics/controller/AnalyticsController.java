package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.*;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.RelatoriosRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private RelatoriosRepository relatoriosRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/vendas-por-categoria")
    public List<VendasPorCategoriaDto> getAllVendasPorCategoria() {
        return relatoriosRepository.vendasPorCategoria(usuarioService.getUsuarioLogado().getId());
    }

    @GetMapping("/geral-produtos")
    public List<VendasPorProdutoDto> getAllProdutosAnalytics() {
        return relatoriosRepository.vendasPorProduto(usuarioService.getUsuarioLogado().getId());
    }

    @GetMapping("/geral-vendedores")
    public List<VendasPorVendedorDto> getAllVendedoresAnalytics() {
        return relatoriosRepository.vendasPorVendedor(usuarioService.getUsuarioLogado().getId());
    }

    @GetMapping("/geral-regioes-personalizados")
    public List<VendasPorRegiaoDto> getAllRegioesPersonalizados() {
        return relatoriosRepository.vendasPorRegiao(usuarioService.getUsuarioLogado().getId());
    }

    @GetMapping("/geral-estados")
    public List<VendasPorEstadoDto> getAllEstados() {
        return relatoriosRepository.vendasPorEstado(usuarioService.getUsuarioLogado().getId());
    }

    @GetMapping("/geral-regioes")
    public List<VendasPorRegiaoAnalyticsDto> getAllAnaliseRegioes() {
        return relatoriosRepository.relatorioVendasPorRegiaoAnalytics(usuarioService.getUsuarioLogado().getId());
    }

    @GetMapping("/geral-fornecedores")
    public List<VendasPorFornecedorDto> getAllAnaliseFornecedores() {
        return relatoriosRepository.vendasPorFornecedor(usuarioService.getUsuarioLogado().getId());
    }

}
