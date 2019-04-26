package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.DtoTeste;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.VendasPorPeriodoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RelatoriosRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String consulta() {
        return "SELECT v.id as id, v.mes_compra as mes, c.nome as cliente, SUM(p.preco) as lucro " +
                "FROM venda v " +
                "LEFT JOIN cliente c ON c.id = v.cliente_id " +
                "LEFT JOIN produto_venda pv ON v.id = pv.venda_id " +
                "LEFT JOIN produto p ON p.id = pv.produto_id " +
                "GROUP BY v.id, v.mes_compra, c.nome";
    }

    public List<DtoTeste> listar() {
        return jdbcTemplate.query(consulta(),
                (rs, rowNum) -> new DtoTeste(
                        rs.getInt("id"),
                        rs.getString("mes"),
                        rs.getString("cliente"),
                        rs.getDouble("lucro")));
    }

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

}
