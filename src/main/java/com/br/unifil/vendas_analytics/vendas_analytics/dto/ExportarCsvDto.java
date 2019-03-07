package com.br.unifil.vendas_analytics.vendas_analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SqlResultSetMapping(name = "ExportarCsvDto", classes = {
        @ConstructorResult(targetClass = ExportarCsvDto.class, columns = {
                @ColumnResult(name = "id", type = Integer.class),
                @ColumnResult(name = "nome_cliente", type = String.class),
                @ColumnResult(name = "cpf_cliente", type = String.class),
                @ColumnResult(name = "email_cliente", type = String.class),
                @ColumnResult(name = "endereco_cliente", type = String.class),
                @ColumnResult(name = "cidade", type = String.class),
                @ColumnResult(name = "estado", type = String.class),
                @ColumnResult(name = "regiao", type = String.class),
                @ColumnResult(name = "usuario_cliente", type = String.class),
                @ColumnResult(name = "codigo_venda", type = Integer.class),
                @ColumnResult(name = "quantidade_itens", type = Integer.class),
                @ColumnResult(name = "data_venda", type = String.class),
                @ColumnResult(name = "situacao_venda", type = String.class),
                @ColumnResult(name = "aprovacao_venda", type = String.class),
                @ColumnResult(name = "codigo_produto", type = Integer.class),
                @ColumnResult(name = "produto", type = String.class),
                @ColumnResult(name = "valor_pedido", type = Double.class),
                @ColumnResult(name = "categoria", type = String.class),
                @ColumnResult(name = "cnpj_fornecedor", type = String.class),
                @ColumnResult(name = "fornecedor_nome_fantasia", type = String.class),
                @ColumnResult(name = "razao_social_fornecedor", type = String.class)
        })
})
public class ExportarCsvDto {

    @Id
    private Integer id;
    private String nome_cliente;
    private String cpf_cliente;
    private String email_cliente;
    private String endereco_cliente;
    private String cidade;
    private String estado;
    private String regiao;
    private String usuario_cliente;
    private Integer codigo_venda;
    private Integer quantidade_itens;
    private String data_venda;
    private String situacao_venda;
    private String aprovacao_venda;
    private Integer codigo_produto;
    private String produto;
    private Double valor_pedido;
    private String categoria;
    private String cnpj_fornecedor;
    private String fornecedor_nome_fantasia;
    private String razao_social_fornecedor;

}
