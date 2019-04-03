package com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "VENDAS_CONCRETIZADAS")
@Data
@NoArgsConstructor
public class vendas_feitas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "vendas_concluidas")
    private int vendasConcluidas;

    @Column(name = "Meses")
    private String meses;

}