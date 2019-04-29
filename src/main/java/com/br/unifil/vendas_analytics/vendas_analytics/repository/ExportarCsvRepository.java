package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.ExportarCsvDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExportarCsvRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;




    private String exportarCsvFiltroData(String dataInicial, String dataFinal) {
        return "SELECT " +
                " c.NOME  AS  nome_cliente , " +
                " c.CPF  AS  cpf_cliente, " +
                " c.EMAIL  AS  email_cliente , " +
                " CONCAT(c.RUA " +
                " , ' - nº ', c.NUMERO)AS  endereco_cliente , " +
                " c.CIDADE  AS  cidade , " +
                " es.ESTADO  AS  estado , " +
                " r.NOME  AS  regiao , " +
                " u.NOME  AS  usuario_cliente ," +
                " v.ID  AS  codigo_venda , " +
                " pv.QUANTIDADE " +
                " AS  quantidade_itens , " +
                " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)) as data_venda, " +
                " v.SITUACAO  AS  situacao_venda , " +
                " v.aprovacao  AS  aprovacao_venda , " +
                " p.ID  AS  codigo_produto , " +
                " p.NOME_PRODUTO AS  produto , " +
                " p.PRECO  AS  valor_pedido , " +
                " ct.DESCRICAO AS  categoria , " +
                " f.CNPJ  AS  cnpj_fornecedor , " +
                " f.NOME_FANTASIA AS  fornecedor_nome_fantasia , " +
                " f.RAZAO_SOCIAL AS  razao_social_fornecedor "  +
                "FROM Cliente c " +
                " LEFT JOIN Usuario u ON c.id = u.cliente_id " +
                " LEFT JOIN Estado es ON es.id = c.estado_id " +
                " LEFT JOIN Regiao r ON es.regiao_id = r.id " +
                " LEFT JOIN Venda v ON v.cliente_id = c.id " +
                " LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id " +
                " LEFT JOIN Produto p ON p.id = pv.produto_id " +
                " LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id " +
                " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id " +
                " WHERE v.DATA_COMPRA BETWEEN CAST('" + dataInicial + "' AS DATE) AND  CAST('" + dataFinal + "' AS DATE)";
    }

    public List<ExportarCsvDto> exportarCsvComFiltroDeData(String dataInicial, String dataFinal) {
        return jdbcTemplate.query(exportarCsvFiltroData(dataInicial, dataFinal),
                (rs, rowNum) -> new ExportarCsvDto(
                        rs.getString("nome_cliente"),
                        rs.getString("cpf_cliente"),
                        rs.getString("email_cliente"),
                        rs.getString("endereco_cliente"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("regiao"),
                        rs.getString("usuario_cliente"),
                        rs.getInt("codigo_venda"),
                        rs.getInt("quantidade_itens"),
                        rs.getString("data_venda"),
                        rs.getString("situacao_venda"),
                        rs.getString("aprovacao_venda"),
                        rs.getInt("codigo_produto"),
                        rs.getString("produto"),
                        rs.getDouble("valor_pedido"),
                        rs.getString("categoria"),
                        rs.getString("cnpj_fornecedor"),
                        rs.getString("fornecedor_nome_fantasia"),
                        rs.getString("razao_social_fornecedor")
                        ));
    }


    private String exportarCsvCompleto() {
        return "SELECT " +
                " c.NOME  AS  nome_cliente , " +
                " c.CPF  AS  cpf_cliente, " +
                " c.EMAIL  AS  email_cliente , " +
                " CONCAT(c.RUA " +
                " , ' - nº ', c.NUMERO)AS  endereco_cliente , " +
                " c.CIDADE  AS  cidade , " +
                " es.ESTADO  AS  estado , " +
                " r.NOME  AS  regiao , " +
                " u.NOME  AS  usuario_cliente ," +
                " v.ID  AS  codigo_venda , " +
                " pv.QUANTIDADE " +
                " AS  quantidade_itens , " +
                " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)) as data_venda, " +
                " v.SITUACAO  AS  situacao_venda , " +
                " v.aprovacao  AS  aprovacao_venda , " +
                " p.ID  AS  codigo_produto , " +
                " p.NOME_PRODUTO AS  produto , " +
                " p.PRECO  AS  valor_pedido , " +
                " ct.DESCRICAO AS  categoria , " +
                " f.CNPJ  AS  cnpj_fornecedor , " +
                " f.NOME_FANTASIA AS  fornecedor_nome_fantasia , " +
                " f.RAZAO_SOCIAL AS  razao_social_fornecedor "  +
                "FROM Cliente c " +
                " LEFT JOIN Usuario u ON c.id = u.cliente_id " +
                " LEFT JOIN Estado es ON es.id = c.estado_id " +
                " LEFT JOIN Regiao r ON es.regiao_id = r.id " +
                " LEFT JOIN Venda v ON v.cliente_id = c.id " +
                " LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id " +
                " LEFT JOIN Produto p ON p.id = pv.produto_id " +
                " LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id " +
                " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id ";
    }

    public List<ExportarCsvDto> exportarCsvSemFiltroDeData() {
        return jdbcTemplate.query(exportarCsvCompleto(),
                (rs, rowNum) -> new ExportarCsvDto(
                        rs.getString("nome_cliente"),
                        rs.getString("cpf_cliente"),
                        rs.getString("email_cliente"),
                        rs.getString("endereco_cliente"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("regiao"),
                        rs.getString("usuario_cliente"),
                        rs.getInt("codigo_venda"),
                        rs.getInt("quantidade_itens"),
                        rs.getString("data_venda"),
                        rs.getString("situacao_venda"),
                        rs.getString("aprovacao_venda"),
                        rs.getInt("codigo_produto"),
                        rs.getString("produto"),
                        rs.getDouble("valor_pedido"),
                        rs.getString("categoria"),
                        rs.getString("cnpj_fornecedor"),
                        rs.getString("fornecedor_nome_fantasia"),
                        rs.getString("razao_social_fornecedor")
                ));
    }

}
