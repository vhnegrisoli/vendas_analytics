package com.br.unifil.vendas_analytics.vendas_analytics.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "historico_venda")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class HistoricoVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Venda venda;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Produto produto;
}
