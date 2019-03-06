package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Cliente;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Produto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Venda;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ClienteRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

import java.util.List;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HistoricoVendaService historicoVendaService;

    public void save(Venda venda) {
        Cliente cliente = clienteRepository.findById(venda.getClientes().getId())
                .orElseThrow(() -> new ValidationException("Cliente não encontrado."));
        List<Produto> produtos = venda.getProdutos();
        try {
            validarClienteComUsuarioAtivo(cliente);
            vendaRepository.save(venda);
            historicoVendaService.geraHistorico(venda, cliente, produtos);
        } catch (Exception e) {
            throw new ValidationException("Não foi possível salvar a venda.");
        }
    }

    public void validarClienteComUsuarioAtivo(Cliente cliente) {

        Usuario usuario = usuarioRepository.findByClienteId(cliente.getId())
                .orElseThrow(() -> new ValidationException("Usuário não existente"));
        if (!usuario.getSituacao().equals(ATIVO)) {
            throw new ValidationException("Não é possível salvar uma venda de um cliente que não possua um usuário" +
                    " ATIVO.");
        }
    }



}
