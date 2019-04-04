package com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "VENDAS_ANALISE_DASHBOARD")
@Data
@NoArgsConstructor
public class Vendas_Analise_Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "quantidade_de_vendas")
    private int quantidade;

    @Column(name = "lucro_total")
    private double lucro;

    @Column(name = "Clientes")
    private int clientes;

    @Column(name = "Produtos")
    private int produtos;

    @Column(name = "lucro_medio_mensal")
    private double media;

    @Column(name = "meses")
    private String meses;

}