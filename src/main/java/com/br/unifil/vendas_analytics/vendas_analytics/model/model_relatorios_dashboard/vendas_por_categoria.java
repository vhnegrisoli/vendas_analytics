package com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "VENDAS_POR_CATEGORIA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class vendas_por_categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private double lucro;

    @Column
    private int quantidade;

    @Column
    private String categoria;
}
