package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.RelatoriosPowerBi;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.PowerBiRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.service.PowerBiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/relatorios-power-bi")
public class PowerBiController {

    @Autowired
    private PowerBiRepository powerBiRepository;

    @Autowired
    private PowerBiService powerBiService;

    @GetMapping("/todos")
    public List<RelatoriosPowerBi> getAllRelatorios() {
        return powerBiRepository.findAll();
    }

    @GetMapping("/buscar/{id}")
    public List<RelatoriosPowerBi> buscarUm(@PathVariable Integer id) {
        return powerBiService.findByUsuario(id);
    }

}
