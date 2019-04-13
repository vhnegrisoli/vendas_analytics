package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.RelatoriosPowerBi;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.PowerBiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios-power-bi")
public class PowerBiController {

    @Autowired
    private PowerBiRepository powerBiRepository;

    @GetMapping("/todos")
    public List<RelatoriosPowerBi> getAllRelatorios() {
        return powerBiRepository.findAll();
    }

}
