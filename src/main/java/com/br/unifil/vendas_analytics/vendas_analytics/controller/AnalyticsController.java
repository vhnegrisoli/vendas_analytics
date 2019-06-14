package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
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
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return relatoriosRepository.vendasPorCategoria(usuarioService.getUsuarioLogado().getId(),
            usuarioLogado.isSuperAdmin());
    }

    @GetMapping("/geral-produtos")
    public List<VendasPorProdutoDto> getAllProdutosAnalytics() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return relatoriosRepository.vendasPorProduto(usuarioService.getUsuarioLogado().getId(),
            usuarioLogado.isSuperAdmin());
    }

    @GetMapping("/geral-vendedores")
    public List<VendasPorVendedorDto> getAllVendedoresAnalytics() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return relatoriosRepository.vendasPorVendedor(usuarioService.getUsuarioLogado().getId(),
            usuarioLogado.isSuperAdmin());
    }

    @GetMapping("/geral-regioes-personalizados")
    public List<VendasPorRegiaoDto> getAllRegioesPersonalizados() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return relatoriosRepository.vendasPorRegiao(usuarioService.getUsuarioLogado().getId(),
            usuarioLogado.isSuperAdmin());
    }

    @GetMapping("/geral-estados")
    public List<VendasPorEstadoDto> getAllEstados() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return relatoriosRepository.vendasPorEstado(usuarioService.getUsuarioLogado().getId(),
            usuarioLogado.isSuperAdmin());
    }

    @GetMapping("/geral-regioes")
    public List<VendasPorRegiaoAnalyticsDto> getAllAnaliseRegioes() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return relatoriosRepository.relatorioVendasPorRegiaoAnalytics(usuarioService.getUsuarioLogado().getId(),
            usuarioLogado.isSuperAdmin());
    }

    @GetMapping("/geral-fornecedores")
    public List<VendasPorFornecedorDto> getAllAnaliseFornecedores() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        return relatoriosRepository.vendasPorFornecedor(usuarioService.getUsuarioLogado().getId(),
            usuarioLogado.isSuperAdmin());
    }

}
