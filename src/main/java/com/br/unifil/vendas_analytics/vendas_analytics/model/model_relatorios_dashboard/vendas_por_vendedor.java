package com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "VENDAS_POR_VENDEDORES")
@Data
@NoArgsConstructor
public class vendas_por_vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Vendedores")
    private int vendedores;

    @Column(name = "Meses")
    private String meses;

}
