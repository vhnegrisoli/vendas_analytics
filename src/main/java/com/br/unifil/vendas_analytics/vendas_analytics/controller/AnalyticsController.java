package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard.vendas_por_categoria;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.Vendas_Por_CategoriaRepository;
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
    private Vendas_Por_CategoriaRepository vendas_por_categoriaRepository;

    @GetMapping("/vendas-por-categoria")
    public List<vendas_por_categoria> getAllVendasPorCategoria() {
        return vendas_por_categoriaRepository.findAll();
    }

}
