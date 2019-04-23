package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Cliente;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ClienteRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.service.ClienteService;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/todos")
    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Cliente buscaUm(@PathVariable Integer id) throws ValidacaoException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("O cliente não existe!"));
    }

    @PostMapping("/salvar")
    public void salvar(@RequestBody Cliente cliente) throws Exception{
        clienteService.salvarCliente(cliente);
    }

    @GetMapping("/total-clientes")
    public long getTotalClientes() {
        return clienteRepository.count();
    }

    @GetMapping("/remover/{id}")
    public void remover(@PathVariable int id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ValidacaoException("Cliente" +
                " não encontrado."));
        clienteRepository.delete(cliente);
    }
}
