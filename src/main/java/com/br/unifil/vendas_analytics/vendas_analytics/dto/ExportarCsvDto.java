package com.br.unifil.vendas_analytics.vendas_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportarCsvDto {

    private String nomeVendedor;
    private String cpfVendedor;
    private String emailVendedor;
    private String enderecoVendedor;
    private String cidade;
    private String estado;
    private String regiao;
    private String usuarioVendedor;
    private Integer codigoVenda;
    private Integer quantidadeItens;
    private String dataVenda;
    private String situacaoVenda;
    private String aprovacaoVenda;
    private String clienteNome;
    private String clienteEmail;
    private String clienteCpf;
    private Integer codigoProduto;
    private String produto;
    private Double valorPedido;
    private String categoria;
    private String cnpjFornecedor;
    private String fornecedorNomeFantasia;
    private String razaoSocialFornecedor;
}
