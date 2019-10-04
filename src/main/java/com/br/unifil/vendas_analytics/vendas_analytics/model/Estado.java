package com.br.unifil.vendas_analytics.vendas_analytics.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "estado")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class  Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "codigo_ibge")
    @NotNull
    @Basic
    private int codigoIbge;

    @Column
    @NotNull
    @Basic
    private String estado;

    @Column
    @NotNull
    @Basic
    private String sigla;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regiao_id")
    private Regiao regiao;

}
