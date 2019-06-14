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
        Relatório VENDAS POR PRODUTO
     */

    private String relatorioVendasPorProduto(Integer usuarioLogadoId) {
        return "SELECT " +
                "p.nome_produto as produto, " +
                "SUM(pv.quantidade) as quantidade, " +
                "SUM(p.PRECO * pv.quantidade) as lucro, " +
                "CAST(AVG(p.PRECO * pv.QUANTIDADE) as NUMERIC(10,2)) as media " +
                "FROM Produto p INNER JOIN produto_venda pv ON p.id = pv.produto_id " +
                "INNER JOIN Venda v ON v.id = pv.venda_id " +
                "INNER JOIN Vendedor vd ON v.vendedor_id = vd.id " +
                "INNER JOIN usuario u ON u.vendedor_id = vd.id " +
                "WHERE u.id =  " + usuarioLogadoId + " OR u.usuario_proprietario = " + usuarioLogadoId + " " +
                "GROUP BY p.nome_produto";
    }

    public List<VendasPorProdutoDto> vendasPorProduto(Integer usuarioLogadoId) {
        return jdbcTemplate.query(relatorioVendasPorProduto(usuarioLogadoId),
                (rs, rowNum) -> new VendasPorProdutoDto(
                        rs.getString("produto"),
                        rs.getInt("quantidade"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media")));
    }

        /*
        Relatório VENDAS POR FORNECEDOR
     */

    private String relatorioVendasPorFornecedor(Integer usuarioLogadoId) {
        return "SELECT " +
                "SUM(pv.quantidade) as quantidade, " +
                "SUM(p.PRECO * pv.quantidade) as lucro, " +
                "CAST(AVG(p.PRECO * pv.QUANTIDADE) as NUMERIC(10,2)) as media, " +
                "f.nome_fantasia as fornecedor " +
                "FROM Fornecedor f " +
                "INNER JOIN produto p ON f.id = p.fornecedor_id " +
                "INNER JOIN produto_venda pv ON p.id = pv.produto_id " +
                "INNER JOIN Venda v ON v.id = pv.venda_id " +
                "INNER JOIN Vendedor vd ON v.vendedor_id = vd.id " +
                "INNER JOIN usuario u ON u.vendedor_id = vd.id " +
                "WHERE u.id =  " + usuarioLogadoId + " OR u.usuario_proprietario = " + usuarioLogadoId + " " +
                "GROUP BY f.nome_fantasia";
    }

    public List<VendasPorFornecedorDto> vendasPorFornecedor(Integer usuarioLogadoId) {
        return jdbcTemplate.query(relatorioVendasPorFornecedor(usuarioLogadoId),
                (rs, rowNum) -> new VendasPorFornecedorDto(
                        rs.getInt("quantidade"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media"),
                        rs.getString("fornecedor")));
    }

            /*
        Relatório VENDAS POR CATEGORIA
     */

    private String relatorioVendasPorCategoria(Integer usuarioLogadoId) {
        return "SELECT " +
                "SUM(pv.quantidade) as quantidade, " +
                "SUM(p.PRECO * pv.quantidade) as lucro, " +
                "CAST(AVG(p.PRECO * pv.QUANTIDADE) as NUMERIC(10,2)) as media, " +
                "c.descricao as categoria " +
                "FROM Categoria c " +
                "INNER JOIN produto p ON c.id = p.categoria_id " +
                "INNER JOIN produto_venda pv ON p.id = pv.produto_id " +
                "INNER JOIN Venda v ON v.id = pv.venda_id " +
                "INNER JOIN Vendedor vd ON v.vendedor_id = vd.id " +
                "INNER JOIN usuario u ON u.vendedor_id = vd.id " +
                "WHERE u.id =  " + usuarioLogadoId + " OR u.usuario_proprietario = " + usuarioLogadoId + " " +
                "GROUP BY c.descricao";
    }

    public List<VendasPorCategoriaDto> vendasPorCategoria(Integer usuarioLogadoId) {
        return jdbcTemplate.query(relatorioVendasPorCategoria(usuarioLogadoId),
                (rs, rowNum) -> new VendasPorCategoriaDto(
                        rs.getInt("quantidade"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media"),
                        rs.getString("categoria")));
    }

        /*
        Relatório VENDAS POR VENDEDORES
     */

    private String relatorioVendasPorVendedor(Integer usuarioLogadoId) {
        return "SELECT " +
                "c.nome as cliente, " +
                "SUM(pv.quantidade) as quantidade, " +
                "SUM(p.PRECO * pv.quantidade) as lucro, " +
                "CAST(AVG(p.PRECO * pv.QUANTIDADE) as NUMERIC(10,2)) as media " +
                "FROM Vendedor c " +
                "INNER JOIN venda v ON v.vendedor_id = c.id " +
                "INNER JOIN produto_venda pv ON pv.venda_id = v.id " +
                "INNER JOIN produto p ON p.id = pv.produto_id " +
                "INNER JOIN usuario u ON u.vendedor_id = c.id " +
                "WHERE u.id =  " + usuarioLogadoId + " OR u.usuario_proprietario = " + usuarioLogadoId + " " +
                "GROUP BY c.nome";
    }

    public List<VendasPorVendedorDto> vendasPorVendedor(Integer usuarioLogadoId) {
        return jdbcTemplate.query(relatorioVendasPorVendedor(usuarioLogadoId),
                (rs, rowNum) -> new VendasPorVendedorDto(
                        rs.getString("cliente"),
                        rs.getInt("quantidade"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media")));
    }

            /*
        Relatório VENDAS POR REGIAO
     */

    private String relatorioVendasPorRegiao(Integer usuarioLogadoId) {
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
                "INNER JOIN usuario u ON u.vendedor_id = c.id " +
                "WHERE u.id =  " + usuarioLogadoId + " OR u.usuario_proprietario = " + usuarioLogadoId + " " +
                "GROUP BY r.nome";
    }

    public List<VendasPorRegiaoDto> vendasPorRegiao(Integer usuarioLogadoId) {
        return jdbcTemplate.query(relatorioVendasPorRegiao(usuarioLogadoId),
                (rs, rowNum) -> new VendasPorRegiaoDto(
                        rs.getString("regiao"),
                        rs.getInt("quantidade"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media")));
    }


    private String relatorioVendasPorRegiaoAnalyticsQuery(Integer usuarioLogadoId) {
        return "SELECT   " +
                "  COUNT(v.id) as qtdVendas, " +
                "  SUM(p.preco * pv.quantidade) as lucro, " +
                "  AVG(p.preco * pv.quantidade)  as media, " +
                "  SUM(pv.quantidade) as qtdProdutos, " +
                "  COUNT(c.ID) as qtdVendedores, " +
                "  r.nome as regiao, " +
                "  e.estado as estado " +
                "FROM Regiao r " +
                "INNER JOIN estado e ON r.id = e.regiao_id " +
                "INNER JOIN vendedor c ON c.estado_id = e.id " +
                "INNER JOIN venda v ON v.vendedor_id = c.id " +
                "INNER JOIN produto_venda pv ON pv.venda_id = v.id " +
                "INNER JOIN produto p ON p.id = pv.produto_id " +
                "INNER JOIN usuario u ON c.id = u.vendedor_id " +
                "WHERE u.id = " + usuarioLogadoId + " OR u.usuario_proprietario = " + usuarioLogadoId +
                " GROUP BY r.nome, e.estado; ";
    }

    public List<VendasPorRegiaoAnalyticsDto> relatorioVendasPorRegiaoAnalytics(Integer usuarioLogadoId) {
        return jdbcTemplate.query(relatorioVendasPorRegiaoAnalyticsQuery(usuarioLogadoId),
                (rs, rowNum) -> new VendasPorRegiaoAnalyticsDto(
                        rs.getInt("qtdVendas"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media"),
                        rs.getInt("qtdProdutos"),
                        rs.getInt("qtdVendedores"),
                        rs.getString("regiao"),
                        rs.getString("estado")
                ));
    }

                /*
        Relatório VENDAS POR ESTADOS
     */

    private String relatorioVendasPorEstado(Integer usuarioLogadoId) {
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
                "INNER JOIN usuario u ON u.vendedor_id = c.id " +
                "WHERE u.id =  " + usuarioLogadoId + " OR u.usuario_proprietario = " + usuarioLogadoId + " " +
                "GROUP BY e.estado";
    }

    public List<VendasPorEstadoDto> vendasPorEstado(Integer usuarioLogadoId) {
        return jdbcTemplate.query(relatorioVendasPorEstado(usuarioLogadoId),
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
                "LEFT JOIN Venda v ON v.id = pv.venda_id " +
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
