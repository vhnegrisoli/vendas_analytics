package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Categoria;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.CategoriaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/todas")
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    @PostMapping("/salvar")
    public void save(@RequestBody Categoria categoria) throws ValidationException {
        try {
            categoriaRepository.save(categoria);
        } catch (Exception e) {
            throw new ValidationException("Não foi possível salvar a categoria.");
        }
    }

    @GetMapping("/remover/{id}")
    public void remover(@PathVariable int id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new ValidacaoException("Categoria" +
                " não encontrada."));
        categoriaRepository.delete(categoria);
    }

}
