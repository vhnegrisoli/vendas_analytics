package com.br.unifil.vendas_analytics.vendas_analytics.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "produto_venda")
public class ProdutoVenda implements Serializable{

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProdutoVendaId id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", referencedColumnName = "id")
    @MapsId("venda_id")
    private Venda venda;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("produto_id")
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private Produto produto;

    @Column
    @Basic
    @NotNull
    private int quantidade;

    public ProdutoVenda() {
    }

    public ProdutoVenda(ProdutoVendaId id) {
        this.id = id;
    }

    public ProdutoVenda(ProdutoVendaId id, int quantidade) {
        this.id = id;
        this.quantidade = quantidade;
    }

    public ProdutoVenda(int produtoId, int vendaId) {
        this.id = new ProdutoVendaId(produtoId, vendaId);
    }

    public ProdutoVendaId getId() {
        return id;
    }

    public void setId(ProdutoVendaId id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoVenda that = (ProdutoVenda) o;
        return quantidade == that.quantidade &&
                Objects.equals(id, that.id) &&
                Objects.equals(produto, that.produto) &&
                Objects.equals(venda, that.venda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, produto, venda, quantidade);
    }
}
