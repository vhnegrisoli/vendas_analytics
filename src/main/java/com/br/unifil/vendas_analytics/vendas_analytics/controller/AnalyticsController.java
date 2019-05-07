package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.*;
import com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_analytics.VendasRegioes;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.RelatoriosRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.repository_analytics.VendasRegioesRepository;
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
    private VendasRegioesRepository vendasRegioesRepository;

    @GetMapping("/vendas-por-categoria")
    public List<VendasPorCategoriaDto> getAllVendasPorCategoria() {
        return relatoriosRepository.vendasPorCategoria();
    }

    @GetMapping("/geral-produtos")
    public List<VendasPorProdutoDto> getAllProdutosAnalytics() {
        return relatoriosRepository.vendasPorProduto();
    }

    @GetMapping("/geral-clientes")
    public List<VendasPorClienteDto> getAllClientesAnalytics() {
        return relatoriosRepository.vendasPorCliente();
    }

    @GetMapping("/geral-regioes-personalizados")
    public List<VendasPorRegiaoDto> getAllRegioesPersonalizados() {
        return relatoriosRepository.vendasPorRegiao();
    }

    @GetMapping("/geral-estados")
    public List<VendasPorEstadoDto> getAllEstados() {
        return relatoriosRepository.vendasPorEstado();
    }

    @GetMapping("/geral-regioes")
    public List<VendasRegioes> getAllAnaliseRegioes() {
        return vendasRegioesRepository.findAll();
    }

    @GetMapping("/geral-fornecedores")
    public List<VendasPorFornecedorDto> getAllAnaliseFornecedores() {
        return relatoriosRepository.vendasPorFornecedor();
    }

}
