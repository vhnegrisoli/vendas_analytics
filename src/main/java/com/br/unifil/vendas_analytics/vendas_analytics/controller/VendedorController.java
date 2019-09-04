package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Vendedor;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendedorRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.service.VendedorService;
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
        return vendedorService.buscarTodos();
    }

    @GetMapping("/buscar/{id}")
    public Vendedor buscaUm(@PathVariable Integer id) {
        return vendedorService.buscarUm(id);
    }

    @PostMapping("/salvar")
    public void salvar(@RequestBody Vendedor vendedor) {
        vendedorService.salvarVendedor(vendedor);
    }

    @GetMapping("/remover/{id}")
    public void remover(@PathVariable int id) {
        vendedorService.validarVendedorComUsuarioComVendasVinculadas(id);
    }
}
