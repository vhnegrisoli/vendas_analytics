package com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_analytics;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "VENDAS_POR_REGIOES")
@Data
@NoArgsConstructor
public class VendasRegioes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int qtdVendas;

    @Column
    private double lucro;

    @Column
    private double media;

    @Column
    private int qtdProdutos;

    @Column
    private int qtdClientes;

    @Column
    private String regiao;

    @Column
    private String estado;

}