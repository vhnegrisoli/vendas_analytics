package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Produto;
import com.br.unifil.vendas_analytics.vendas_analytics.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/todos")
    public List<Produto> getAllProdutos() {
        return produtoService.buscarTodos();
    }

    @PostMapping("/salvar")
    public void save(@RequestBody Produto produto) {
        produtoService.save(produto);
    }

    @GetMapping("buscar/{id}")
    public Produto findOne(@PathVariable Integer id) {
        return produtoService.buscarUm(id);
    }

    @GetMapping("/total-produtos")
    public long getTotalProdutos() {
        return produtoService.getTotalProdutos();
    }

    @GetMapping("/remover/{id}")
    public void remover(@PathVariable Integer id) {
        produtoService.remover(id);
    }
}
