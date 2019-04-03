package com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "VENDAS_POR_PRODUTO")
@Data
@NoArgsConstructor
public class vendas_por_produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Produtos")
    private int produtos;

    @Column(name = "Meses")
    private String meses;

}