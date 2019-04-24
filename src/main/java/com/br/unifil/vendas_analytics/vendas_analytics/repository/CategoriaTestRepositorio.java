package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.DtoTeste;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriaTestRepositorio {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String consulta() {
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

}
