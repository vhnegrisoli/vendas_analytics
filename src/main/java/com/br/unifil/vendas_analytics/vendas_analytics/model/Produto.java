package com.br.unifil.vendas_analytics.vendas_analytics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome_produto")
    @Basic
    @NotNull
    private String nomeProduto;

    @Column
    @Basic
    @NotNull
    private String descricao;

    @Column
    @Basic
    @NotNull
    private double preco;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    Fornecedor fornecedor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    Categoria categoria;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<ProdutoVenda> venda;

    @NotNull
    @Column
    @Basic
    private Integer usuarioCadastro;
}
