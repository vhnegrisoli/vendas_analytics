package com.br.unifil.vendas_analytics.vendas_analytics.model;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum;
import com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "venda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantidade_de_itens")
    @Basic
    @NotNull
    private int quantidadeItens;

    @Column(name = "data_compra")
    @Basic
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dataCompra;

    @Column
    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    private VendaSituacaoEnum situacao;

    @Column
    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    private VendaAprovacaoEnum aprovacao;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "cliente_venda",
               joinColumns = @JoinColumn(name = "venda_id", insertable = false, updatable = false),
               inverseJoinColumns = @JoinColumn(name = "cliente_id", insertable = false, updatable = false))
    private List<Cliente> clientes;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "produto_venda",
            joinColumns = @JoinColumn(name = "venda_id", insertable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "produto_id", insertable = false, updatable = false))
    private List<Produto> produtos;
}
