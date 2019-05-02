package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Categoria;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Fornecedor;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.FornecedorRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
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

    @GetMapping("buscar/{id}")
    public Fornecedor findOne(@PathVariable Integer id) {
        return fornecedorRepository.findById(id).orElseThrow(() -> new ValidacaoException("Fornecedor não encontrado"));
    }

    @GetMapping("/remover/{id}")
    public void remover(@PathVariable int id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
            .orElseThrow(() -> new ValidacaoException("Fornecedor não encontrado."));
        try {
            fornecedorRepository.delete(fornecedor);
        } catch (Exception e) {
            throw new ValidacaoException("Não é possível remover o fornecedor " + fornecedor.getNomeFantasia()
                    + " pois ele já está definido para outros produtos.");
        }
    }
}
