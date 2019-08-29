package com.br.unifil.vendas_analytics.vendas_analytics.model;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vendedor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Vendedor {

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
    private LocalDate dataNascimento;

    @Column
    @Basic
    @NotNull
    private String rua;

    @Column
    @Basic
    @NotNull
    private String cep;

    @Column
    @Basic
    private String complemento;

    @NotNull
    @Basic
    private String cidade;

    @Column
    @Basic
    @NotNull
    private int numero;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id")
    private Estado estado;

}
