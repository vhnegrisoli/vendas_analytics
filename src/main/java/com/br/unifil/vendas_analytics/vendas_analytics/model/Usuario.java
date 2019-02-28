package com.br.unifil.vendas_analytics.vendas_analytics.model;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column
    @Basic
    private String nome;

    @NotNull
    @Column(name = "data_cadastro")
    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;

    @NotNull
    @Column
    @Basic
    private String email;

    @NotNull
    @Column
    @Basic
    private String senha;

    @NotNull
    @Column
    @Basic
    @Enumerated(EnumType.STRING)
    private UsuarioSituacao situacao;
}
