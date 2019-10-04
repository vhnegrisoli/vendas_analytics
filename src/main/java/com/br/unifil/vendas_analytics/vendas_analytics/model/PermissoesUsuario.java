package com.br.unifil.vendas_analytics.vendas_analytics.model;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.PermissoesUsuarioEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "permissoes_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissoesUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private PermissoesUsuarioEnum permissao;

    @Column
    @NotNull
    private String descricao;

}
