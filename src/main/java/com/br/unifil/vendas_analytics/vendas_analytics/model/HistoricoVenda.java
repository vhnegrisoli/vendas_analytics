package com.br.unifil.vendas_analytics.vendas_analytics.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "HISTORICO_DE_VENDA")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class HistoricoVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo_venda")
    private int codigoVenda;

    @Column(name = "situacao_venda")
    private String situacaoVenda;

    @Column(name = "aprovacao_venda")
    private String aprovacaoVenda;

    @Column(name = "quantidade_itens")
    private int quantidade;

    @Column(name = "nome_vendedor")
    private String nomeVendedor;

    @Column(name = "email_vendedor")
    private String emailVendedor;

    @Column(name = "endereco_vendedor")
    private String enderecoVendedor;

    @Column(name = "local_vendedor")
    private String localVendedor;

    @Column(name = "nome_produto")
    private String nomeProduto;

    @Column(name = "descricao_produto")
    private String descricaoProduto;

    @Column
    private double preco;

}
