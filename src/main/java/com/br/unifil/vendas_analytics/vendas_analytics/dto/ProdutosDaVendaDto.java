package com.br.unifil.vendas_analytics.vendas_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@SqlResultSetMapping(name = "ProdutosDaVendaDto", classes = {
        @ConstructorResult(targetClass = ExportarCsvDto.class, columns = {
                @ColumnResult(name = "id", type = Integer.class),
                @ColumnResult(name = "produto", type = String.class),
                @ColumnResult(name = "descricao", type = String.class),
                @ColumnResult(name = "fornecedor", type = String.class),
                @ColumnResult(name = "categoria", type = String.class),
                @ColumnResult(name = "preco", type = Double.class),
                @ColumnResult(name = "quantidade", type = Integer.class)
        })
})
public class ProdutosDaVendaDto {

    @Id
    private Integer id;
    private String produto;
    private String descricao;
    private String fornecedor;
    private String categoria;
    private Double preco;
    private Integer quantidade;

}
