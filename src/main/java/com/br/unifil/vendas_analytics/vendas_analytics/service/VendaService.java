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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum.AGUARDANDO_APROVACAO;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum.APROVADA;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum.ABERTA;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum.FECHADA;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum.FINALIZADA;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void save(Venda venda) {
        Cliente cliente = clienteRepository.findById(venda.getClientes().getId())
                .orElseThrow(() -> new ValidationException("Cliente não encontrado."));
        try {
            validarClienteComUsuarioAtivo(cliente);
            valindarVendaFechadaOuConcluida(venda);
            venda = validarInformacoesVenda(venda);
            validarRejeicaoVenda(venda);
            vendaRepository.save(venda);
        } catch (Exception e) {
            throw new ValidationException("Não foi possível salvar a venda.");
        }
    }

    public void valindarVendaFechadaOuConcluida(Venda venda) {
        if (venda.getSituacao().equals(FECHADA)
            || venda.getSituacao().equals(FINALIZADA)) {
            throw new ValidationException("A venda está " + venda.getSituacao() + ", ou seja, " +
                    "não é possível fazer alterações.");
        }
    }

    public Venda validarInformacoesVenda(Venda venda) {
        if (venda.getAprovacao().equals(APROVADA)) {
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            venda.setDataCompra(date);
            venda.setSituacao(ABERTA);
        } else if (venda.getAprovacao().equals(AGUARDANDO_APROVACAO)) {

        }

        return venda;
    }

    public void validarRejeicaoVenda(Venda venda) {

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
