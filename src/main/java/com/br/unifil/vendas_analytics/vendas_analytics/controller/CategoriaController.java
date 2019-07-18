package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Categoria;
import com.br.unifil.vendas_analytics.vendas_analytics.service.CategoriaService;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/todas")
    public List<Categoria> getAllCategorias() {
        return categoriaService.buscarTodos();
    }

    @PostMapping("/salvar")
    public void save(@RequestBody Categoria categoria) {
        categoriaService.save(categoria);
    }

    @GetMapping("buscar/{id}")
    public Categoria findOne(@PathVariable Integer id) {
        return categoriaService.buscarUm(id);
    }

    @GetMapping("/remover/{id}")
    public void remover(@PathVariable Integer id) {
        categoriaService.remover(id);
    }

}
