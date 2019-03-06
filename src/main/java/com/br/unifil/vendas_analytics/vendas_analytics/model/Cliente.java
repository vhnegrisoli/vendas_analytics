package com.br.unifil.vendas_analytics.vendas_analytics.model;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column
    @Basic
    private String nome;

    @NotNull
    @Column
    @Basic
    private String email;

    @NotNull
    @Column
    @Basic
    @CPF
    private String cpf;

    @NotNull
    @Column
    @Basic
    private String rg;

    @NotNull
    @Column
    @Basic
    private String telefone;

    @NotNull
    @Column(name = "data_nascimento")
    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

}
