package com.br.unifil.vendas_analytics.vendas_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendasAnaliseDashboardDto {

    private int quantidade;
    private double lucro;
    private double media;
    private int vendedores;
    private int produtos;
    private String meses;

}
