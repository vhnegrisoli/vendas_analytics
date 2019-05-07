package com.br.unifil.vendas_analytics.vendas_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardsDashboardDto {

    private long qtdClientes;
    private long qtdProdutos;
    private long qtdVendasRealizadas;
    private long qtdVendasNaoRealizadas;

}
