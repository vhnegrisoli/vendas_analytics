package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.repository.VendaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class RelatorioCsvService {

    @Autowired
    private VendaRepository vendaRepository;

    public String gerarCabecalho() {
        return "Nome do Cliente;CPF do Cliente;Email do Cliente;Endereço do Cliente;Cidade;Estado;" +
                "Região;Usuário do Cliente;Código da Venda;Quantidade de Itens;Data da Venda;Situação" +
                " da Venda;Aprovação da Venda;Código do Produto;Produto;Valor do Pedido;Categoria;CNPJ do Fornecedor;" +
                "Nome do Fornecedor;Razão Social do Fornecedor";
    }

    public String gerarCsv(String dataInicial, String dataFinal) throws JsonProcessingException {
        List<Object> resposta = vendaRepository.getRelatoriosCsv(dataInicial, dataFinal);
        String dadosVenda = gerarCabecalho() + "\n";
        Iterator itr = resposta.iterator();
        while(itr.hasNext()) {
            Object element = itr.next();
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String [] json = ow.writeValueAsString(element).split(",");
            dadosVenda = dadosVenda +
                    json[1].replaceAll("\"", "") + ";" +
                    json[2].replaceAll("\"", "")  + ";" +
                    json[3].replaceAll("\"", "")  + ";" +
                    json[4].replaceAll("\"", "")  + ";" +
                    json[5].replaceAll("\"", "")  + ";" +
                    json[6].replaceAll("\"", "")  + ";" +
                    json[7].replaceAll("\"", "")  + ";" +
                    json[8].replaceAll("\"", "")  + ";" +
                    json[9].replaceAll("\"", "")  + ";" +
                    json[10].replaceAll("\"", "")  + ";" +
                    json[11].replaceAll("\"", "")  + ";" +
                    json[12].replaceAll("\"", "")  + ";" +
                    json[13].replaceAll("\"", "")  + ";" +
                    json[14].replaceAll("\"", "")  + ";" +
                    json[15].replaceAll("\"", "")  + ";" +
                    json[16].replaceAll("\"", "")  + ";" +
                    json[17].replaceAll("\"", "")  + ";" +
                    json[18].replaceAll("\"", "")  + ";" +
                    json[19].replaceAll("\"", "")  + ";" +
                    json[20].replaceAll("]", "")
                            .replaceAll("\"", "") + "\n";
        }
        return dadosVenda;
    }

}
