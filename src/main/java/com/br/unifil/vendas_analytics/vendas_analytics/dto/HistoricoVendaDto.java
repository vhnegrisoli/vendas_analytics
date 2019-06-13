package com.br.unifil.vendas_analytics.vendas_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricoVendaDto {

    private int codigoVenda;
    private String situacaoVenda;
    private String aprovacaoVenda;
    private String mesVenda;
    private int quantidade;
    private String nomeVendedor;
    private String emailVendedor;
    private String enderecoVendedor;
    private String localVendedor;
    private String nomeProduto;
    private String descricaoProduto;
    private double preco;

}
