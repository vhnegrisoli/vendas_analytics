package com.br.unifil.vendas_analytics.vendas_analytics.model;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum;
import com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Entity
@Table(name = "venda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Venda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_compra")
    @Basic
    @NotNull
    private LocalDateTime dataCompra;

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

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ProdutoVenda> produtos;

    @Column(name = "mes_compra")
    private String mesCompra;

    @Column(name = "cliente_nome")
    private String clienteNome;

    @Column(name = "cliente_email")
    private String clienteEmail;

    @Column(name = "cliente_cpf")
    private String clienteCpf;

    @JsonIgnore
    public boolean isNovaVenda() {
        return isEmpty(this.id);
    }
}
