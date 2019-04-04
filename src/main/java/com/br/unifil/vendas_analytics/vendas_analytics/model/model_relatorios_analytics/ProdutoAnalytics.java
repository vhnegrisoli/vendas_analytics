package com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_analytics;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "LUCRO_MEDIA_PRODUTO")
@Data
@NoArgsConstructor
public class ProdutoAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String produto;

    @Column
    private int quantidade;

    @Column
    private double media;

}