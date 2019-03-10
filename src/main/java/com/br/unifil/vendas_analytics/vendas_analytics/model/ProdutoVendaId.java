package com.br.unifil.vendas_analytics.vendas_analytics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProdutoVendaId implements Serializable {


    @Column(name = "venda_id")
    private Integer vendaId;

    @Column(name = "produto_id")
    private Integer produtoId;

    public ProdutoVendaId(Integer produtoId, Integer vendaId) {
        this.produtoId = produtoId;
        this.vendaId = vendaId;
    }

    public ProdutoVendaId() {
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getVendaId() {
        return vendaId;
    }

    public void setVendaId(Integer vendaId) {
        this.vendaId = vendaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoVendaId that = (ProdutoVendaId) o;
        return Objects.equals(produtoId, that.produtoId) &&
                Objects.equals(vendaId, that.vendaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produtoId, vendaId);
    }
}
