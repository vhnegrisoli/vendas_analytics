package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Cliente;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Endereco;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ClienteRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.EnderecoRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.service.ClienteService;
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

    @PostMapping("/salvar")
    public void salvar(@RequestBody Cliente cliente) throws Exception{

        clienteService.salvarCliente(cliente);
    }
}
