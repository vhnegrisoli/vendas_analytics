package com.br.unifil.vendas_analytics.vendas_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendasPorRegiaoAnalyticsDto {

    private int qtdVendas;
    private double lucro;
    private double media;
    private int qtdProdutos;
    private int qtdVendedores;
    private String regiao;
    private String estado;

}
