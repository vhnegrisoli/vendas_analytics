package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Produto;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/todos")
    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    @PostMapping("/salvar")
    public void save(@RequestBody Produto produto) throws ValidationException {
        try {
            produtoRepository.save(produto);
        } catch (Exception e) {
            throw new ValidationException("Não foi possível salvar o produto");
        }
    }

    @GetMapping("/total-produtos")
    public long getTotalProdutos() {
        return produtoRepository.count();
    }

}
