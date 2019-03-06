package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Fornecedor;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    FornecedorRepository fornecedorRepository;

    @GetMapping("/todos")
    public List<Fornecedor> getAllFornecedores() {
        return fornecedorRepository.findAll();
    }

    @PostMapping("/salvar")
    public void save(@RequestBody Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
    }

}
