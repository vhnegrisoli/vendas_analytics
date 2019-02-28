package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Cliente;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    UsuarioService usuarioService;

    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        criaUsuarioAoInserirCliente(cliente);
        return cliente;
    }

    public void criaUsuarioAoInserirCliente(Cliente cliente) {
        Usuario usuario = new Usuario();
    }

}
