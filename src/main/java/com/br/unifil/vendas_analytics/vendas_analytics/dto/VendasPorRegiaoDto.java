package com.br.unifil.vendas_analytics.vendas_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendasPorRegiaoDto {

    private String regiao;
    private int quantidade;
    private double lucro;
    private double media;

}
