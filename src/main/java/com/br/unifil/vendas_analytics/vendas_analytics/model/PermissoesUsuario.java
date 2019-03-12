package com.br.unifil.vendas_analytics.vendas_analytics.model;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.PermissoesUsuarioEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "permissoes_usuario")
public class PermissoesUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private PermissoesUsuarioEnum permissao;

    @Column
    @NotNull
    private String descricao;

}
