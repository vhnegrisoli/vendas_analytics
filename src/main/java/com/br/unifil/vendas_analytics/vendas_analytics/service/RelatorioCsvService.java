package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.ExportarCsvDto2;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ExportarCsvRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RelatorioCsvService {

    @Autowired
    private ExportarCsvRepository exportarCsvRepository;

    public String gerarCabecalho() {
        return "Nome do Cliente;CPF do Cliente;Email do Cliente;Endereço do Cliente;Cidade;Estado;" +
                "Região;Usuário do Cliente;Código da Venda;Quantidade de Itens;Data da Venda;Situação" +
                " da Venda;Aprovação da Venda;Código do Produto;Produto;Valor do Pedido;Categoria;CNPJ do Fornecedor;" +
                "Nome do Fornecedor;Razão Social do Fornecedor";
    }

    public String gerarCsv(String dataInicial, String dataFinal) throws JsonProcessingException {
        List<ExportarCsvDto2> resposta = new ArrayList<>();
        if (ObjectUtils.isEmpty(dataInicial) || ObjectUtils.isEmpty(dataFinal)) {
            resposta = exportarCsvRepository.exportarCsvSemFiltroDeData();
        } else {
            resposta = exportarCsvRepository.exportarCsvComFiltroDeData(dataInicial, dataFinal);
        }
        AtomicReference<String> dadosVenda = new AtomicReference<>(gerarCabecalho() + "\n");
        resposta
            .forEach(
                registro -> {
                    dadosVenda.set(dadosVenda.get() +
                            registro.getNome_cliente() + ";" +
                            registro.getCpf_cliente() + ";" +
                            registro.getEmail_cliente() + ";" +
                            registro.getEndereco_cliente() + ";" +
                            registro.getCidade() + ";" +
                            registro.getEstado() + ";" +
                            registro.getRegiao() + ";" +
                            registro.getUsuario_cliente() + ";" +
                            registro.getCodigo_venda() + ";" +
                            registro.getQuantidade_itens() + ";" +
                            registro.getData_venda() + ";" +
                            registro.getSituacao_venda() + ";" +
                            registro.getAprovacao_venda() + ";" +
                            registro.getCodigo_produto() + ";" +
                            registro.getProduto() + ";" +
                            registro.getValor_pedido() + ";" +
                            registro.getCategoria() + ";" +
                            registro.getCnpj_fornecedor() + ";" +
                            registro.getFornecedor_nome_fantasia() + ";" +
                            registro.getRazao_social_fornecedor() + ";\n");
                }
            );
        return dadosVenda.get();
    }

}
