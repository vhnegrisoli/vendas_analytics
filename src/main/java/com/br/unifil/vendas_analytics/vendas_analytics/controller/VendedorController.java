package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Vendedor;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendedorRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.service.VendedorService;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private VendedorService vendedorService;

    @GetMapping("/todos")
    public List<Vendedor> buscarTodos() {
        return vendedorRepository.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Vendedor buscaUm(@PathVariable Integer id) throws ValidacaoException {
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("O vendedor não existe!"));
    }

    @PostMapping("/salvar")
    public void salvar(@RequestBody Vendedor vendedor) throws Exception{
        vendedorService.salvarCliente(vendedor);
    }

    @GetMapping("/remover/{id}")
    public void remover(@PathVariable int id) {
        vendedorService.removerClienteComUsuarioComVendasVinculadas(id);
    }
}
