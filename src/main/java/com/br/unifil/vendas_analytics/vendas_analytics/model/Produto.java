package com.br.unifil.vendas_analytics.vendas_analytics.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
    @JoinColumn(name = "fornecedor_id", insertable = false, updatable = false)
    Fornecedor fornecedor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "categoria_id", insertable = false, updatable = false)
    Categoria categoria;

    @ManyToMany(mappedBy = "produtos", cascade = CascadeType.PERSIST)
    private List<Venda> vendas;
}