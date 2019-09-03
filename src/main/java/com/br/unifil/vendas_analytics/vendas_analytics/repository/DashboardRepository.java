package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DashboardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
        RELATÓRIO VENDAS POR PERÍODO - DASHBOARD
     */

    private String vendasPorPeriodoUserAdmin(Integer usuarioLogadoId) {
        return "SELECT " +
            "SUM(\"quantidade\") AS quantidade, " +
            "SUM(\"lucro\") AS lucro, " +
            "SUM(\"media\") AS media, " +
            "\"meses\" AS meses " +
            "FROM (" +
            "SELECT " +
            "COUNT(v.id) AS quantidade, " +
            "SUM(p.preco * pv.quantidade) AS lucro, " +
            "AVG(p.preco * pv.quantidade) AS media, " +
            "v.mes_compra AS meses " +
            "FROM VENDA v " +
            "INNER JOIN produto_venda pv ON v.id = pv.venda_id " +
            "INNER JOIN produto p ON p.id = pv.produto_id " +
            "INNER JOIN vendedor vd ON vd.id = v.vendedor_id " +
            "INNER JOIN usuario u ON u.vendedor_id = vd.id " +
            "WHERE u.id =  " + usuarioLogadoId +
            "GROUP BY v.mes_compra " +
            "UNION " +
            "SELECT " +
            "COUNT(v.id) AS quantidade, " +
            "SUM(p.preco) AS lucro, " +
            "AVG(p.preco) AS media, " +
            "v.mes_compra AS meses " +
            "FROM VENDA v " +
            "INNER JOIN produto_venda pv ON v.id = pv.venda_id " +
            "INNER JOIN produto p ON p.id = pv.produto_id " +
            "INNER JOIN vendedor vd ON vd.id = v.vendedor_id " +
            "INNER JOIN usuario u ON u.vendedor_id = vd.id " +
            "WHERE u.usuario_proprietario =  " + usuarioLogadoId +
            "GROUP BY v.mes_compra " +
            ") m " +
            "GROUP BY m.\"meses\";";
    }

    private String vendasPorPeriodoSuperAdmin() {
        return "SELECT " +
            "COUNT(v.id) AS quantidade, " +
            "SUM(p.preco) AS lucro, " +
            "AVG(p.preco) AS media, " +
            "v.mes_compra AS meses " +
            "FROM VENDA v " +
            "INNER JOIN produto_venda pv ON v.id = pv.venda_id " +
            "INNER JOIN produto p ON p.id = pv.produto_id " +
            "INNER JOIN vendedor vd ON vd.id = v.vendedor_id " +
            "GROUP BY v.mes_compra";
    }

    public List<VendasPorPeriodoDto> vendasPorPeriodo(Integer usuarioLogadoId, boolean isSuperAdmin) {
        if (isSuperAdmin) {
            return jdbcTemplate.query(vendasPorPeriodoSuperAdmin(),
                (rs, rowNum) -> new VendasPorPeriodoDto(
                    rs.getInt("quantidade"),
                    rs.getDouble("lucro"),
                    rs.getDouble("media"),
                    rs.getString("meses")));
        } else {
            return jdbcTemplate.query(vendasPorPeriodoUserAdmin(usuarioLogadoId),
                (rs, rowNum) -> new VendasPorPeriodoDto(
                    rs.getInt("quantidade"),
                    rs.getDouble("lucro"),
                    rs.getDouble("media"),
                    rs.getString("meses")));
        }
    }

    /*
        VALORES UNITÁRIOS DOS CARDS DA DASHBOARD
     */

    private String totalCardsAdminUserQuery(Integer usuarioLogadoId) {
        return "SELECT DISTINCT " +
            "( " +
            "(SELECT COUNT(v.ID) FROM vendedor v " +
            "INNER JOIN Usuario u ON u.vendedor_id = v.id " +
            "WHERE u.id = " + usuarioLogadoId + ") " +
            "+ " +
            "(SELECT COUNT(v.ID) FROM vendedor v " +
            "INNER JOIN Usuario u ON u.vendedor_id = v.id " +
            "WHERE u.usuario_proprietario = " + usuarioLogadoId + ") " +
            ") AS qtdClientes, " +
            "( " +
            "SELECT COUNT(DISTINCT p.ID) FROM Produto p " +
            "INNER JOIN produto_venda pv ON pv.produto_id = p.id " +
            "INNER JOIN venda v ON pv.venda_id = v.id " +
            "INNER JOIN vendedor vd ON vd.id = v.vendedor_id " +
            "INNER JOIN usuario u ON vd.id = u.vendedor_id " +
            "WHERE u.id = " + usuarioLogadoId + " AND pv.produto_id = p.id) AS qtdProdutos, " +
            "( " +
            "(SELECT COUNT(DISTINCT v.id) FROM Venda v " +
            "INNER JOIN vendedor vd ON vd.id = v.vendedor_id " +
            "INNER JOIN usuario u ON vd.id = u.vendedor_id " +
            "WHERE u.id = " + usuarioLogadoId + "  " +
            "AND v.situacao = 'FECHADA' AND v.aprovacao = 'APROVADA' ) " +
            "+ " +
            "(SELECT COUNT(DISTINCT v.id) FROM Venda v " +
            "INNER JOIN vendedor vd ON vd.id = v.vendedor_id " +
            "INNER JOIN usuario u ON vd.id = u.vendedor_id " +
            "WHERE u.usuario_proprietario = " + usuarioLogadoId + "  " +
            "AND v.situacao = 'FECHADA' AND v.aprovacao = 'APROVADA' ) " +
            ") AS qtdVendasRealizadas, " +
            "( " +
            "(SELECT COUNT(DISTINCT v.id) FROM Venda v " +
            "INNER JOIN vendedor vd ON vd.id = v.vendedor_id " +
            "INNER JOIN usuario u ON vd.id = u.vendedor_id " +
            "WHERE u.id = " + usuarioLogadoId + "  " +
            "AND v.aprovacao = 'REJEITADA' )  " +
            "+ " +
            "(SELECT COUNT(DISTINCT v.id) FROM Venda v " +
            "INNER JOIN vendedor vd ON vd.id = v.vendedor_id " +
            "INNER JOIN usuario u ON vd.id = u.vendedor_id " +
            "WHERE u.usuario_proprietario = " + usuarioLogadoId + "  " +
            "AND v.aprovacao = 'REJEITADA' ) " +
            ") " +
            "AS qtdVendasNaoRealizadas " +
            "FROM VENDEDOR;";
    }

    private String totalCardsSuperAdmin() {
        return "SELECT DISTINCT " +
            "( " +
            "(SELECT COUNT(v.ID) FROM vendedor v) " +
            ") AS qtdClientes, " +
            "( " +
            "SELECT COUNT(DISTINCT p.ID) FROM Produto p) AS qtdProdutos, " +
            "( " +
            "(SELECT COUNT(DISTINCT v.id) FROM VENDA v " +
            "WHERE v.situacao = 'FECHADA' AND v.aprovacao = 'APROVADA' ) " +
            ") AS qtdVendasRealizadas, " +
            "( " +
            "(SELECT COUNT(DISTINCT v.id) FROM Venda v " +
            "WHERE v.aprovacao <> 'APROVADA' )  " +
            ") " +
            "AS qtdVendasNaoRealizadas " +
            "FROM VENDEDOR;";
    }

    public List<CardsDashboardDto> totalVendedores(Integer usuarioLogadoId, boolean isSuperAdmin) {
        if (isSuperAdmin) {
            return jdbcTemplate.query(totalCardsSuperAdmin(),
                (rs, rowNum) -> new CardsDashboardDto(
                    rs.getLong("qtdClientes"),
                    rs.getLong("qtdProdutos"),
                    rs.getLong("qtdVendasRealizadas"),
                    rs.getLong("qtdVendasNaoRealizadas")));
        } else {
            return jdbcTemplate.query(totalCardsAdminUserQuery(usuarioLogadoId),
                (rs, rowNum) -> new CardsDashboardDto(
                    rs.getLong("qtdClientes"),
                    rs.getLong("qtdProdutos"),
                    rs.getLong("qtdVendasRealizadas"),
                    rs.getLong("qtdVendasNaoRealizadas")));
        }
    }

    /*
        RELATÓRIO VENDAS ANÁLISE DASHBOARD - SUPER ADMIN
     */

    private String vendasAnaliseDashboardSuperAdmin() {
        return "SELECT  " +
            "COUNT(v.id) AS quantidade_de_vendas,   " +
            "SUM(p.preco * pv.quantidade) AS lucro_total,  " +
            "AVG(p.preco * pv.quantidade) AS lucro_medio_mensal, " +
            "COUNT(c.ID) AS Vendedores, " +
            "COUNT(p.ID) AS Produtos, " +
            "v.mes_compra AS meses " +
            "FROM VENDA v  " +
            "INNER JOIN vendedor c ON c.id = v.vendedor_id " +
            "INNER JOIN produto_venda pv ON v.id = pv.venda_id " +
            "INNER JOIN produto p ON p.id = pv.produto_id " +
            "GROUP BY v.mes_compra;";
    }


    /*
        RELATÓRIO VENDAS ANÁLISE DASHBOARD - USER E ADMIN
     */

    private String vendasAnaliseDashboardUserAdmin(Integer usuarioLogadoId) {
        return
            "SELECT " +
            "SUM(m.\"quantidade_de_vendas\") AS quantidade_de_vendas, " +
            "SUM(m.\"lucro_total\") AS lucro_total, " +
            "SUM(m.\"lucro_medio_mensal\") AS lucro_medio_mensal, " +
            "SUM(m.\"Vendedores\") AS Vendedores, " +
            "SUM(m.\"Produtos\") AS Produtos, " +
            "\"meses\" AS meses " +
            "FROM ( " +
            "SELECT  " +
            "COUNT(v.id) AS quantidade_de_vendas,   " +
            "SUM(p.preco * pv.quantidade) AS lucro_total,  " +
            "AVG(p.preco * pv.quantidade) AS lucro_medio_mensal, " +
            "COUNT(c.ID) AS Vendedores, " +
            "COUNT(p.ID) AS Produtos, " +
            "v.mes_compra AS meses " +
            "FROM VENDA v  " +
            "INNER JOIN vendedor c ON c.id = v.vendedor_id " +
            "INNER JOIN produto_venda pv ON v.id = pv.venda_id " +
            "INNER JOIN produto p ON p.id = pv.produto_id " +
            "INNER JOIN usuario u ON u.vendedor_id = c.id " +
            "WHERE u.id = " + usuarioLogadoId +
            " GROUP BY v.mes_compra" +
            " UNION " +
            "SELECT  " +
            "COUNT(v.id) AS quantidade_de_vendas,   " +
            "SUM(p.preco * pv.quantidade) AS lucro_total,  " +
            "AVG(p.preco * pv.quantidade) AS lucro_medio_mensal, " +
            "COUNT(c.ID) AS Vendedores, " +
            "COUNT(p.ID) AS Produtos, " +
            "v.mes_compra AS meses " +
            "FROM VENDA v  " +
            "INNER JOIN vendedor c ON c.id = v.vendedor_id " +
            "INNER JOIN produto_venda pv ON v.id = pv.venda_id " +
            "INNER JOIN produto p ON p.id = pv.produto_id " +
            "INNER JOIN usuario u ON u.vendedor_id = c.id " +
            "WHERE u.usuario_proprietario = " + usuarioLogadoId +
            " GROUP BY v.mes_compra" +
            ") m " +
            "GROUP BY m.\"meses\";";
    }

    public List<VendasAnaliseDashboardDto> vendasAnaliseDashboard(Integer usuarioLogadoId, boolean isSuperAdmin) {
        if (isSuperAdmin) {
            return jdbcTemplate.query(vendasAnaliseDashboardSuperAdmin(),
                (rs, rowNum) -> new VendasAnaliseDashboardDto(
                    rs.getInt("quantidade_de_vendas"),
                    rs.getDouble("lucro_total"),
                    rs.getDouble("lucro_medio_mensal"),
                    rs.getInt("Produtos"),
                    rs.getInt("Vendedores"),
                    rs.getString("meses")));
        } else {
            return jdbcTemplate.query(vendasAnaliseDashboardUserAdmin(usuarioLogadoId),
                (rs, rowNum) -> new VendasAnaliseDashboardDto(
                    rs.getInt("quantidade_de_vendas"),
                    rs.getDouble("lucro_total"),
                    rs.getDouble("lucro_medio_mensal"),
                    rs.getInt("Produtos"),
                    rs.getInt("Vendedores"),
                    rs.getString("meses")));
        }
    }

    /*
        RELATÓRIO VENDAS REALIZADAS - SUPER ADMIN
     */

    private String vendasRealizadasDashboardUserAdmin(Integer usuarioLogadoId) {
        return "SELECT " +
            "SUM(m.\"vendas_concluidas\") AS vendas_concluidas, " +
            "m.\"meses\" AS meses " +
            "FROM (" +
            "SELECT " +
            "COUNT(v.ID) AS vendas_concluidas, " +
            "v.mes_compra AS meses " +
            "FROM VENDA v " +
            "INNER JOIN Vendedor vd ON v.vendedor_id = vd.id " +
            "INNER JOIN Usuario u ON u.vendedor_id = vd.id " +
            "WHERE u.id = " + usuarioLogadoId +
            " AND v.SITUACAO = 'FECHADA' AND v.APROVACAO = 'APROVADA'" +
            "GROUP BY v.mes_compra " +
            "UNION " +
            "SELECT " +
            "COUNT(v.ID) AS vendas_concluidas, " +
            "v.mes_compra AS meses " +
            "FROM VENDA v " +
            "INNER JOIN Vendedor vd ON v.vendedor_id = vd.id " +
            "INNER JOIN Usuario u ON u.vendedor_id = vd.id " +
            "WHERE u.usuario_proprietario = " + usuarioLogadoId +
            " AND v.SITUACAO = 'FECHADA' AND v.APROVACAO = 'APROVADA'" +
            "GROUP BY v.mes_compra " +
            ") m " +
            "GROUP BY m.\"meses\";";
    }

    /*
        RELATÓRIO VENDAS REALIZADAS - USER E ADMIN
     */

    private String vendasRealizadasDashboardSuperAdmin() {
        return "SELECT " +
            "COUNT(v.ID) AS vendas_concluidas, " +
            "v.mes_compra AS meses " +
            "FROM VENDA v " +
            "WHERE v.SITUACAO = 'FECHADA' AND v.APROVACAO = 'APROVADA'" +
            "GROUP BY v.mes_compra;";
    }

    public List<VendasSituacoesDto> vendasRealizadasDashboard(Integer usuarioLogadoId, boolean isSuperAdmin) {
        if (isSuperAdmin) {
            return jdbcTemplate.query(vendasRealizadasDashboardSuperAdmin(),
                (rs, rowNum) -> new VendasSituacoesDto(
                    rs.getInt("vendas_concluidas"),
                    rs.getString("meses")));
        } else {
            return jdbcTemplate.query(vendasRealizadasDashboardUserAdmin(usuarioLogadoId),
                (rs, rowNum) -> new VendasSituacoesDto(
                    rs.getInt("vendas_concluidas"),
                    rs.getString("meses")));
        }
    }


    /*
        RELATÓRIO VENDAS NÃO REALIZADAS - SUPER ADMIN
     */

    private String vendasNaoRealizadasDashboardUserAdmin(Integer usuarioLogadoId) {
        return "SELECT " +
            "SUM(m.\"vendas_nao_concluidas\") AS vendas_nao_concluidas, " +
            "m.\"meses\" AS meses " +
            "FROM (" +
            "SELECT " +
            "COUNT(v.ID) AS vendas_nao_concluidas, " +
            "v.mes_compra AS meses " +
            "FROM VENDA v " +
            "INNER JOIN Vendedor vd ON v.vendedor_id = vd.id " +
            "INNER JOIN Usuario u ON u.vendedor_id = vd.id " +
            "WHERE u.id = " + usuarioLogadoId +
            " AND v.APROVACAO = 'REJEITADA'" +
            "GROUP BY v.mes_compra" +
            " UNION " +
            "SELECT " +
            "COUNT(v.ID) AS vendas_nao_concluidas, " +
            "v.mes_compra AS meses " +
            "FROM VENDA v " +
            "INNER JOIN Vendedor vd ON v.vendedor_id = vd.id " +
            "INNER JOIN Usuario u ON u.vendedor_id = vd.id " +
            "WHERE u.usuario_proprietario = " + usuarioLogadoId +
            " AND v.APROVACAO = 'REJEITADA'" +
            "GROUP BY v.mes_compra " +
            ") m " +
            "GROUP BY m.\"meses\";";
    }

    /*
        RELATÓRIO VENDAS REALIZADAS - USER E ADMIN
     */

    private String vendasNaoRealizadasDashboardSuperAdmin() {
        return "SELECT " +
            "COUNT(v.ID) AS vendas_nao_concluidas, " +
            "v.mes_compra AS meses " +
            "FROM VENDA v " +
            "WHERE v.APROVACAO = 'REJEITADA'" +
            "GROUP BY v.mes_compra;";
    }

    public List<VendasSituacoesDto> vendasNaoRealizadasDashboard(Integer usuarioLogadoId, boolean isSuperAdmin) {
        if (isSuperAdmin) {
            return jdbcTemplate.query(vendasNaoRealizadasDashboardSuperAdmin(),
                (rs, rowNum) -> new VendasSituacoesDto(
                    rs.getInt("vendas_nao_concluidas"),
                    rs.getString("meses")));
        } else {
            return jdbcTemplate.query(vendasNaoRealizadasDashboardUserAdmin(usuarioLogadoId),
                (rs, rowNum) -> new VendasSituacoesDto(
                    rs.getInt("vendas_nao_concluidas"),
                    rs.getString("meses")));
        }
    }

    /*
        RELATÓRIO VENDAS POR VENDEDORES DO DASHBOARD - SUPER ADMIN
     */

    private String vendasPorVendedoresDashboardUserAdmin(Integer usuarioLogadoId) {
        return "SELECT " +
            "COUNT(c.ID) AS Vendedores, " +
            "v.MES_COMPRA as meses " +
            "FROM Vendedor c " +
            "INNER JOIN Venda v ON c.id = v.vendedor_id " +
            "INNER JOIN Usuario u ON u.vendedor_id = c.id " +
            "WHERE u.id = " +  usuarioLogadoId + " OR u.usuario_proprietario = " + usuarioLogadoId +
            " GROUP BY v.MES_COMPRA;";
    }

    /*
        RELATÓRIO VENDAS POR VENDEDORES DO DASHBOARD - USER E ADMIN
     */

    private String vendasPorVendedoresDashboardSuperAdmin() {
        return "SELECT " +
            "COUNT(c.ID) AS Vendedores, " +
            "v.MES_COMPRA as meses " +
            "FROM Vendedor c " +
            "INNER JOIN Venda v ON c.id = v.vendedor_id " +
            "GROUP BY v.MES_COMPRA;";
    }

    public List<VendasVendedoresDashboardDto> vendasPorVendedores(Integer usuarioLogadoId, boolean isSuperAdmin) {
        if (isSuperAdmin) {
            return jdbcTemplate.query(vendasPorVendedoresDashboardSuperAdmin(),
                (rs, rowNum) -> new VendasVendedoresDashboardDto(
                    rs.getInt("Vendedores"),
                    rs.getString("meses")));
        } else {
            return jdbcTemplate.query(vendasPorVendedoresDashboardUserAdmin(usuarioLogadoId),
                (rs, rowNum) -> new VendasVendedoresDashboardDto(
                    rs.getInt("Vendedores"),
                    rs.getString("meses")));
        }
    }

    /*
        RELATÓRIO VENDAS POR PRODUTOS DO DASHBOARD - SUPER ADMIN
     */

    private String vendasPorProdutosDashboardUserAdmin(Integer usuarioLogadoId) {
        return "SELECT " +
            "COUNT(p.ID) AS Produtos, " +
            "v.mes_compra AS Meses " +
            "FROM Produto p " +
            "INNER JOIN produto_venda pv ON p.id = pv.produto_id " +
            "INNER JOIN Venda v ON v.id = pv.venda_id " +
            "INNER JOIN Vendedor vd ON v.vendedor_id = vd.id " +
            "INNER JOIN Usuario u ON u.vendedor_id = vd.id " +
            "WHERE u.id = " + usuarioLogadoId + " OR u.usuario_proprietario = " + usuarioLogadoId +
            " GROUP BY v.mes_compra;";
    }

    /*
        RELATÓRIO VENDAS POR PRODUTOS DO DASHBOARD - USER E ADMIN
     */

    private String vendasPorProdutosDashboardSuperAdmin() {
        return "SELECT " +
            "COUNT(p.ID) AS Produtos, " +
            "v.mes_compra AS Meses " +
            "FROM Produto p " +
            "INNER JOIN produto_venda pv ON p.id = pv.produto_id " +
            "INNER JOIN Venda v ON v.id = pv.venda_id " +
            "GROUP BY v.mes_compra;";
    }

    public List<VendasProdutosDashboardDto> vendasPorProdutos(Integer usuarioLogadoId, boolean isSuperAdmin) {
        if (isSuperAdmin) {
            return jdbcTemplate.query(vendasPorProdutosDashboardSuperAdmin(),
                (rs, rowNum) -> new VendasProdutosDashboardDto(
                    rs.getInt("Produtos"),
                    rs.getString("Meses")));
        } else {
            return jdbcTemplate.query(vendasPorProdutosDashboardUserAdmin(usuarioLogadoId),
                (rs, rowNum) -> new VendasProdutosDashboardDto(
                    rs.getInt("Produtos"),
                    rs.getString("Meses")));
        }
    }
}
