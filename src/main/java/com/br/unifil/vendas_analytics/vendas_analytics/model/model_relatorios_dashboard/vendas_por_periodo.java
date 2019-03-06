package com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "VENDAS_POR_PERIODO")
@Data
@NoArgsConstructor
public class vendas_por_periodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantidade_de_vendas")
    private int quantidade;

    @Column(name = "lucro_total")
    private double lucro;

    @Column(name = "meses")
    private String meses;

}
