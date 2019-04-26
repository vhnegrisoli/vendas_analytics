package com.br.unifil.vendas_analytics.vendas_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportarCsvDto2 {

    private String nome_cliente;
    private String cpf_cliente;
    private String email_cliente;
    private String endereco_cliente;
    private String cidade;
    private String estado;
    private String regiao;
    private String usuario_cliente;
    private Integer codigo_venda;
    private Integer quantidade_itens;
    private String data_venda;
    private String situacao_venda;
    private String aprovacao_venda;
    private Integer codigo_produto;
    private String produto;
    private Double valor_pedido;
    private String categoria;
    private String cnpj_fornecedor;
    private String fornecedor_nome_fantasia;
    private String razao_social_fornecedor;

}
