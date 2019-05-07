package com.br.unifil.vendas_analytics.vendas_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendasPorCategoriaDto {

    private int quantidade;
    private double lucro;
    private double media;
    private String categoria;

}
