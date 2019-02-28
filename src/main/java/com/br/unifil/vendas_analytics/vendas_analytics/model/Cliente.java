package com.br.unifil.vendas_analytics.vendas_analytics.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToMany(mappedBy = "clientes", cascade = CascadeType.PERSIST)
    private List<Venda> vendas;
}
