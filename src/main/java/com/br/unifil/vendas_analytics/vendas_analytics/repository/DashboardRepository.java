package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.CardsDashboardDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.VendasPorPeriodoDto;
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
                "COUNT(v.id) AS quantidade, " +
                "SUM(p.preco) AS lucro, " +
                "AVG(p.preco) AS media, " +
                "v.mes_compra AS meses " +
                "FROM VENDA v " +
                "INNER JOIN produto_venda pv ON v.id = pv.venda_id " +
                "INNER JOIN produto p ON p.id = pv.produto_id " +
                "INNER JOIN vendedor vd ON vd.id = v.vendedor_id " +
                "INNER JOIN usuario u ON u.vendedor_id = vd.id " +
                "WHERE u.id =  " + usuarioLogadoId + " OR u.usuario_proprietario = " + usuarioLogadoId + " " +
                "GROUP BY v.mes_compra";
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
                "AND v.aprovacao <> 'APROVADA' )  " +
                "+ " +
                "(SELECT COUNT(DISTINCT v.id) FROM Venda v " +
                "INNER JOIN vendedor vd ON vd.id = v.vendedor_id " +
                "INNER JOIN usuario u ON vd.id = u.vendedor_id " +
                "WHERE u.usuario_proprietario = " + usuarioLogadoId + "  " +
                "AND v.aprovacao <> 'APROVADA' ) " +
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
}
