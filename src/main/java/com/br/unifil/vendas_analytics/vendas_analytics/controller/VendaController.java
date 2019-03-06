package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Venda;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    VendaRepository vendaRepository;

    @GetMapping("/todas")
    public List<Venda> getAllVendas() {
        return vendaRepository.findAll();
    }

    @PostMapping("/salvar")
    public void save(@RequestBody Venda venda) {
        vendaRepository.save(venda);
    }

}