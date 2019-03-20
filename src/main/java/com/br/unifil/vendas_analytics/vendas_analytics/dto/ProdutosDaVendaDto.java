package com.br.unifil.vendas_analytics.vendas_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SqlResultSetMapping(name = "ProdutosDaVendaDto", classes = {
        @ConstructorResult(targetClass = ExportarCsvDto.class, columns = {
                @ColumnResult(name = "id", type = Integer.class),
                @ColumnResult(name = "nome_produto", type = String.class),
                @ColumnResult(name = "descricao_produto", type = String.class),
                @ColumnResult(name = "razao_social_fornecedor", type = String.class),
                @ColumnResult(name = "categoria_produto", type = String.class),
                @ColumnResult(name = "preco_produto", type = Double.class),
                @ColumnResult(name = "quantidade_produto", type = Integer.class)
        })
})
public class ProdutosDaVendaDto {

    @Id
    private Integer id;
    private String nome_produto;
    private String descricao_produto;
    private String razao_social_fornecedor;
    private String categoria_produto;
    private Double preco_produto;
    private Integer quantidade_produto;

}
