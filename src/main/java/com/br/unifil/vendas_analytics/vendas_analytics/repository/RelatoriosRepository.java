package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.VendasPorPeriodoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.VendasPorProdutoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RelatoriosRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
        Relatório VENDAS POR PERÍODO
     */

    private String relatorioVendasPorPeriodo() {
        return "SELECT " +
                "COUNT(v.id) AS quantidade, " +
                "SUM(p.preco) AS lucro, " +
                "AVG(p.preco) AS media, " +
                "v.mes_compra AS meses " +
                "FROM VENDA v " +
                "INNER JOIN produto_venda pv ON v.id = pv.venda_id " +
                "INNER JOIN produto p ON p.id = pv.produto_id " +
                "GROUP BY v.mes_compra";
    }

    public List<VendasPorPeriodoDto> vendasPorPeriodo() {
        return jdbcTemplate.query(relatorioVendasPorPeriodo(),
                (rs, rowNum) -> new VendasPorPeriodoDto(
                        rs.getInt("quantidade"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media"),
                        rs.getString("meses")));
    }

    /*
        Relatório VENDAS POR PRODUTO
     */

    private String relatorioVendasPorProduto() {
        return "SELECT " +
                "p.nome_produto as produto, " +
                "SUM(pv.quantidade) as quantidade, " +
                "SUM(p.PRECO * pv.quantidade) as lucro, " +
                "CAST(AVG(p.PRECO * pv.QUANTIDADE) as NUMERIC(10,2)) as media " +
                "FROM Produto p INNER JOIN produto_venda pv ON p.id = pv.produto_id " +
                "GROUP BY p.nome_produto";
    }

    public List<VendasPorProdutoDto> vendasPorProduto() {
        return jdbcTemplate.query(relatorioVendasPorProduto(),
                (rs, rowNum) -> new VendasPorProdutoDto(
                        rs.getString("produto"),
                        rs.getInt("quantidade"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media")));
    }

}
