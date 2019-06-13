package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.VendasPorPeriodoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DashboardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String relatorioVendasPorPeriodo(Integer usuarioLogadoId) {
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

    public List<VendasPorPeriodoDto> vendasPorPeriodo(Integer usuarioLogadoId) {
        return jdbcTemplate.query(relatorioVendasPorPeriodo(usuarioLogadoId),
                (rs, rowNum) -> new VendasPorPeriodoDto(
                        rs.getInt("quantidade"),
                        rs.getDouble("lucro"),
                        rs.getDouble("media"),
                        rs.getString("meses")));
    }


}
