package com.br.unifil.vendas_analytics.vendas_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendasPorFornecedorDto {

    private int qtdVendas;
    private double lucro;
    private double media;
    private String fornecedor;

}
