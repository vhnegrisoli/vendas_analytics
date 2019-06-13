package com.br.unifil.vendas_analytics.vendas_analytics.repository;


import com.br.unifil.vendas_analytics.vendas_analytics.dto.HistoricoVendaDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.VendasPorPeriodoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoricoVendaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String historicoDeVendaQuery(Integer usuarioLogadoId) {
        return "SELECT " +
                " v.ID   AS codigoVenda, " +
                " v.SITUACAO  AS situacaoVenda, " +
                " v.APROVACAO  AS aprovacaoVenda, " +
                " v.MES_COMPRA AS mesVenda, " +
                " pv.quantidade AS quantidadeItens, " +
                " c.nome   AS nomeVendedor, " +
                " c.email   AS emailVendedor, " +
                " CONCAT(c.rua " +
                "    ,', nº',c.numero) AS enderecoVendedor, " +
                " CONCAT(c.cidade, " +
                " ' - ', e.estado) AS localVendedor, " +
                " p.nome_produto AS nomeProduto, " +
                " p.descricao  AS descricaoProduto, " +
                " p.preco   AS preco " +
                "FROM Venda v " +
                " LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id " +
                " LEFT JOIN Produto p ON p.id = pv.produto_id " +
                " LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id " +
                " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id " +
                " LEFT JOIN Vendedor c ON c.id = v.vendedor_id " +
                " LEFT JOIN Estado e ON e.id = c.estado_id " +
                " LEFT JOIN Regiao r ON e.regiao_id = r.id " +
                " LEFT JOIN Usuario u ON c.id = u.vendedor_id " +
                " WHERE u.id = " + usuarioLogadoId + " OR u.usuario_proprietario = " + usuarioLogadoId;
    }

    public List<HistoricoVendaDto> historicoDeVenda(Integer usuarioLogadoId) {
        return jdbcTemplate.query(historicoDeVendaQuery(usuarioLogadoId),
                (rs, rowNum) -> new HistoricoVendaDto(
                        rs.getInt("codigoVenda"),
                        rs.getString("situacaoVenda"),
                        rs.getString("aprovacaoVenda"),
                        rs.getString("mesVenda"),
                        rs.getInt("quantidadeItens"),
                        rs.getString("nomeVendedor"),
                        rs.getString("emailVendedor"),
                        rs.getString("enderecoVendedor"),
                        rs.getString("localVendedor"),
                        rs.getString("nomeProduto"),
                        rs.getString("descricaoProduto"),
                        rs.getDouble("preco")));
    }

}
