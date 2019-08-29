package com.br.unifil.vendas_analytics.vendas_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutosDaVendaDto {

    private Integer id;
    private String produto;
    private String descricao;
    private String fornecedor;
    private String categoria;
    private Double preco;
    private Integer quantidade;

}
