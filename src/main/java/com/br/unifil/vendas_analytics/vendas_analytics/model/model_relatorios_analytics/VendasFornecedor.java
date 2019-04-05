package com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_analytics;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "VENDAS_POR_FORNECEDORES")
@Data
@NoArgsConstructor
public class VendasFornecedor {

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
    private String fornecedor;

}