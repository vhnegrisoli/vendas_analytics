package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Fornecedor;
import com.br.unifil.vendas_analytics.vendas_analytics.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping("/todos")
    public List<Fornecedor> getAllFornecedores() {
        return fornecedorService.buscarTodos();
    }

    @PostMapping("/salvar")
    public void save(@RequestBody Fornecedor fornecedor) {
        fornecedorService.save(fornecedor);
    }

    @GetMapping("buscar/{id}")
    public Fornecedor findOne(@PathVariable Integer id) {
        return fornecedorService.buscarUm(id);
    }

    @GetMapping("/remover/{id}")
    public void remover(@PathVariable Integer id) {
        fornecedorService.remover(id);
    }
}
