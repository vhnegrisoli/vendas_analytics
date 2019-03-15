package com.br.unifil.vendas_analytics.vendas_analytics.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "fornecedor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column
    @Basic
    private String razaoSocial;

    @NotNull
    @Column
    @Basic
    private String cnpj;

    @NotNull
    @Column
    @Basic
    private String nomeFantasia;

    @NotNull
    @Column
    @Basic
    private String endereco;
}
