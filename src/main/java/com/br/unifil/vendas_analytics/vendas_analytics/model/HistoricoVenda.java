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
    private int id;

    @Column(name = "codigo_venda")
    private int codigoVenda;

    @Column(name = "situacao_venda")
    private String situacaoVenda;

    @Column(name = "aprovacao_venda")
    private String aprovacaoVenda;

    @Column(name = "quantidade_itens")
    private int quantidade;

    @Column(name = "nome_cliente")
    private String nomeCliente;

    @Column(name = "email_cliente")
    private String emailCliente;

    @Column(name = "endereco_cliente")
    private String enderecoCliente;

    @Column(name = "local_cliente")
    private String localCliente;

    @Column(name = "nome_produto")
    private String nomeProduto;

    @Column(name = "descricao_produto")
    private String descricaoProduto;

    @Column
    private double preco;

}
