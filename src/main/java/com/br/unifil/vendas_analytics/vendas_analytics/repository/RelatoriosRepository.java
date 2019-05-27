package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.*;
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

        /*
        Relatório VENDAS POR FORNECEDOR
     */

    private String relatorioVendasPorFornecedor() {
        return "SELECT " +
                "SUM(pv.quantidade) as quantidade, " +
                "SUM(p.PRECO * pv.quantidade) as lucro, " +
                "CAST(AVG(p.PRECO * pv.QUANTIDADE) as NUMERIC(10,2)) as media, " +
                "f.nome_fantasia as fornecedor " +
                "FROM Fornecedor f " +
                "INNER JOIN produto p ON f.id = p.fornecedor_id " +
                "INNER JOIN produto_venda pv ON p.id = pv.produto_id " +
                "GROUP BY f.nome_fantasia";
    }

    public List<VendasPorFornecedorDto> vendasPorFornecedor() {
        return jdbcTemplate.query(relatorioVendasPorFornecedor(),
                (rs, rowNum) -> new VendasPorFornecedorDto(
                        rs.getInt("quantidade"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media"),
                        rs.getString("fornecedor")));
    }

            /*
        Relatório VENDAS POR CATEGORIA
     */

    private String relatorioVendasPorCategoria() {
        return "SELECT " +
                "SUM(pv.quantidade) as quantidade, " +
                "SUM(p.PRECO * pv.quantidade) as lucro, " +
                "CAST(AVG(p.PRECO * pv.QUANTIDADE) as NUMERIC(10,2)) as media, " +
                "c.descricao as categoria " +
                "FROM Categoria c " +
                "INNER JOIN produto p ON c.id = p.categoria_id " +
                "INNER JOIN produto_venda pv ON p.id = pv.produto_id " +
                "GROUP BY c.descricao";
    }

    public List<VendasPorCategoriaDto> vendasPorCategoria() {
        return jdbcTemplate.query(relatorioVendasPorCategoria(),
                (rs, rowNum) -> new VendasPorCategoriaDto(
                        rs.getInt("quantidade"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media"),
                        rs.getString("categoria")));
    }

        /*
        Relatório VENDAS POR VENDEDORES
     */

    private String relatorioVendasPorVendedor() {
        return "SELECT " +
                "c.nome as cliente, " +
                "SUM(pv.quantidade) as quantidade, " +
                "SUM(p.PRECO * pv.quantidade) as lucro, " +
                "CAST(AVG(p.PRECO * pv.QUANTIDADE) as NUMERIC(10,2)) as media " +
                "FROM Vendedor c " +
                "INNER JOIN venda v ON v.vendedor_id = c.id " +
                "INNER JOIN produto_venda pv ON pv.venda_id = v.id " +
                "INNER JOIN produto p ON p.id = pv.produto_id " +
                "GROUP BY c.nome";
    }

    public List<VendasPorVendedorDto> vendasPorVendedor() {
        return jdbcTemplate.query(relatorioVendasPorVendedor(),
                (rs, rowNum) -> new VendasPorVendedorDto(
                        rs.getString("cliente"),
                        rs.getInt("quantidade"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media")));
    }

            /*
        Relatório VENDAS POR REGIAO
     */

    private String relatorioVendasPorRegiao() {
        return "SELECT " +
                "r.nome as regiao, " +
                "SUM(pv.quantidade) as quantidade, " +
                "SUM(p.PRECO * pv.quantidade) as lucro, " +
                "CAST(AVG(p.PRECO * pv.QUANTIDADE) as NUMERIC(10,2)) as media " +
                "FROM regiao r " +
                "INNER JOIN estado e ON e.regiao_id = r.id " +
                "INNER JOIN Vendedor c ON c.estado_id = e.id " +
                "INNER JOIN venda v ON v.vendedor_id = c.id " +
                "INNER JOIN produto_venda pv ON pv.venda_id = v.id " +
                "INNER JOIN produto p ON p.id = pv.produto_id " +
                "GROUP BY r.nome";
    }

    public List<VendasPorRegiaoDto> vendasPorRegiao() {
        return jdbcTemplate.query(relatorioVendasPorRegiao(),
                (rs, rowNum) -> new VendasPorRegiaoDto(
                        rs.getString("regiao"),
                        rs.getInt("quantidade"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media")));
    }

                /*
        Relatório VENDAS POR ESTADOS
     */

    private String relatorioVendasPorEstado() {
        return "SELECT " +
                "e.estado as estado, " +
                "SUM(pv.quantidade) as quantidade, " +
                "SUM(p.PRECO * pv.quantidade) as lucro, " +
                "CAST(AVG(p.PRECO * pv.QUANTIDADE) as NUMERIC(10,2)) as media " +
                "FROM estado e " +
                "INNER JOIN Vendedor c ON c.estado_id = e.id " +
                "INNER JOIN venda v ON v.vendedor_id = c.id " +
                "INNER JOIN produto_venda pv ON pv.venda_id = v.id " +
                "INNER JOIN produto p ON p.id = pv.produto_id " +
                "GROUP BY e.estado";
    }

    public List<VendasPorEstadoDto> vendasPorEstado() {
        return jdbcTemplate.query(relatorioVendasPorEstado(),
                (rs, rowNum) -> new VendasPorEstadoDto(
                        rs.getString("estado"),
                        rs.getInt("quantidade"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media")));
    }

                /*
        CARDS DAS VENDAS
     */

    private String getProdutosDaVendaByVendaId(Integer id) {
        return "SELECT " +
                "p.id as id, " +
                "p.nome_produto as produto, " +
                "p.descricao as descricao, " +
                "f.razao_social as fornecedor, " +
                "c.descricao as categoria ," +
                "p.preco as preco, " +
                "pv.quantidade as quantidade " +
                "FROM Produto p " +
                "LEFT JOIN Produto_Venda pv ON p.id = pv.produto_id " +
                "LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id " +
                "LEFT JOIN Categoria c ON c.id = p.categoria_id " +
                "WHERE pv.venda_id = " + id;
    }

    public List<ProdutosDaVendaDto> findAllProdutosDaVendaByVendaId(Integer id) {
        return jdbcTemplate.query(getProdutosDaVendaByVendaId(id),
                (rs, rowNum) -> new ProdutosDaVendaDto(
                        rs.getInt("id"),
                        rs.getString("produto"),
                        rs.getString("descricao"),
                        rs.getString("fornecedor"),
                        rs.getString("categoria"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")));
    }


}
