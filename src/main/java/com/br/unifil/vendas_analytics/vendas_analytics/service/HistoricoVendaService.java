package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Cliente;
import com.br.unifil.vendas_analytics.vendas_analytics.model.HistoricoVenda;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Produto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Venda;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.HistoricoVendaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
public class HistoricoVendaService {

    @Autowired
    private HistoricoVendaRepository historicoVendaRepository;

    @Autowired
    private VendaRepository vendaRepository;

    public void geraHistorico(Venda venda, Cliente cliente, List<Produto> produtos) throws ValidationException {
        vendaRepository.findById(venda.getId()).orElseThrow(() ->
                new ValidationException("A venda não existe."));
        try {
            produtos.forEach(
                    produto -> {
                        HistoricoVenda historicoVenda = HistoricoVenda
                                .builder()
                                .venda(venda)
                                .cliente(cliente)
                                .produto(produto)
                                .build();
                        historicoVendaRepository.save(historicoVenda);
                    }
            );
        } catch (Exception e) {
            throw new ValidationException("Não foi possível gerar o histórico");
        }

    }
}
