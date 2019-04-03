package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard.*;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.repository_relatorios_dashboard.*;
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
    Vendas_Por_PeriodoRepository vendas_por_periodoRepository;

    @Autowired
    Vendas_Por_ClienteRepository vendas_por_clienteRepository;

    @Autowired
    Vendas_Por_ProdutoRepository vendas_por_produtoRepository;

    @Autowired
    Vendas_FeitasRepository vendas_feitasRepository;

    @Autowired
    Vendas_RejeitadasRepository vendas_rejeitadasRepository;

    @GetMapping("/vendas-por-periodo")
    public List<vendas_por_periodo> getAllVendasPorPeriodo() {
        return vendas_por_periodoRepository.findAll();
    }

    @GetMapping("/card1/vendas-por-cliente")
    public List<vendas_por_cliente> getAllVendasPorCliente() {
        return vendas_por_clienteRepository.findAll();
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

}
