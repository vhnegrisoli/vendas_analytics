package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard.vendas_por_periodo;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.repository_relatorios_dashboard.Vendas_Por_PeriodoRepository;
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

    @GetMapping("/vendas-por-periodo")
    public List<vendas_por_periodo> getAllVendasPorPeriodo() {
        return vendas_por_periodoRepository.findAll();
    }

}
