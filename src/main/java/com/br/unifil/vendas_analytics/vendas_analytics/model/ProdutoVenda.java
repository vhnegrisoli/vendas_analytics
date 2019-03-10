package com.br.unifil.vendas_analytics.vendas_analytics.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "produto_venda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoVenda {

    @EmbeddedId
    private ProdutoVendaId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("produto_id")
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private Produto produto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "venda_id", referencedColumnName = "id")
    @MapsId("venda_id")
    private Venda venda;

    @Column
    @Basic
    @NotNull
    private int quantidade;

}
