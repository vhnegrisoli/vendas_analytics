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




    private String exportarCsvFiltroData(String dataInicial, String dataFinal, Integer usuarioLogadoId) {
        return "SELECT " +
                " c.NOME  AS  nome_vendedor , " +
                " c.CPF  AS  cpf_vendedor, " +
                " c.EMAIL  AS  email_vendedor , " +
                " CONCAT(c.RUA " +
                " , ' - nº ', c.NUMERO)AS  endereco_vendedor , " +
                " c.CIDADE  AS  cidade , " +
                " es.ESTADO  AS  estado , " +
                " r.NOME  AS  regiao , " +
                " u.NOME  AS  usuario_vendedor ," +
                " v.ID  AS  codigo_venda , " +
                " pv.QUANTIDADE " +
                " AS  quantidade_itens , " +
                " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)) as data_venda, " +
                " v.SITUACAO  AS  situacao_venda , " +
                " v.aprovacao  AS  aprovacao_venda , " +
                " v.cliente_nome as cliente_nome, " +
                " v.cliente_email as cliente_email, " +
                " v.cliente_cpf as cliente_cpf, " +
                " p.ID  AS  codigo_produto , " +
                " p.NOME_PRODUTO AS  produto , " +
                " p.PRECO  AS  valor_pedido , " +
                " ct.DESCRICAO AS  categoria , " +
                " f.CNPJ  AS  cnpj_fornecedor , " +
                " f.NOME_FANTASIA AS  fornecedor_nome_fantasia , " +
                " f.RAZAO_SOCIAL AS  razao_social_fornecedor "  +
                "FROM Vendedor c " +
                " LEFT JOIN Usuario u ON c.id = u.vendedor_id " +
                " LEFT JOIN Estado es ON es.id = c.estado_id " +
                " LEFT JOIN Regiao r ON es.regiao_id = r.id " +
                " LEFT JOIN Venda v ON v.vendedor_id = c.id " +
                " LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id " +
                " LEFT JOIN Produto p ON p.id = pv.produto_id " +
                " LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id " +
                " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id " +
                " WHERE v.DATA_COMPRA BETWEEN CAST('" + dataInicial + "' AS DATE) AND  CAST('" + dataFinal + "' AS DATE)" +
                " AND u.id = " + usuarioLogadoId +
                " UNION " +
                " SELECT " +
                " c.NOME  AS  nome_vendedor , " +
                " c.CPF  AS  cpf_vendedor, " +
                " c.EMAIL  AS  email_vendedor , " +
                " CONCAT(c.RUA " +
                " , ' - nº ', c.NUMERO)AS  endereco_vendedor , " +
                " c.CIDADE  AS  cidade , " +
                " es.ESTADO  AS  estado , " +
                " r.NOME  AS  regiao , " +
                " u.NOME  AS  usuario_vendedor ," +
                " v.ID  AS  codigo_venda , " +
                " pv.QUANTIDADE " +
                " AS  quantidade_itens , " +
                " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)) as data_venda, " +
                " v.SITUACAO  AS  situacao_venda , " +
                " v.aprovacao  AS  aprovacao_venda , " +
                " v.cliente_nome as cliente_nome, " +
                " v.cliente_email as cliente_email, " +
                " v.cliente_cpf as cliente_cpf, " +
                " p.ID  AS  codigo_produto , " +
                " p.NOME_PRODUTO AS  produto , " +
                " p.PRECO  AS  valor_pedido , " +
                " ct.DESCRICAO AS  categoria , " +
                " f.CNPJ  AS  cnpj_fornecedor , " +
                " f.NOME_FANTASIA AS  fornecedor_nome_fantasia , " +
                " f.RAZAO_SOCIAL AS  razao_social_fornecedor "  +
                "FROM Vendedor c " +
                " LEFT JOIN Usuario u ON c.id = u.vendedor_id " +
                " LEFT JOIN Estado es ON es.id = c.estado_id " +
                " LEFT JOIN Regiao r ON es.regiao_id = r.id " +
                " LEFT JOIN Venda v ON v.vendedor_id = c.id " +
                " LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id " +
                " LEFT JOIN Produto p ON p.id = pv.produto_id " +
                " LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id " +
                " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id " +
                " WHERE v.DATA_COMPRA BETWEEN CAST('" + dataInicial + "' AS DATE) AND  CAST('" + dataFinal + "' AS DATE)" +
                " AND u.usuario_proprietario = " + usuarioLogadoId;
    }

    public List<ExportarCsvDto> exportarCsvComFiltroDeData(String dataInicial,
                                                           String dataFinal,
                                                           Integer usuarioLogadoId) {
        return jdbcTemplate.query(exportarCsvFiltroData(dataInicial, dataFinal, usuarioLogadoId),
                (rs, rowNum) -> new ExportarCsvDto(
                        rs.getString("nome_vendedor"),
                        rs.getString("cpf_vendedor"),
                        rs.getString("email_vendedor"),
                        rs.getString("endereco_vendedor"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("regiao"),
                        rs.getString("usuario_vendedor"),
                        rs.getInt("codigo_venda"),
                        rs.getInt("quantidade_itens"),
                        rs.getString("data_venda"),
                        rs.getString("situacao_venda"),
                        rs.getString("aprovacao_venda"),
                        rs.getString("cliente_nome"),
                        rs.getString("cliente_email"),
                        rs.getString("cliente_cpf"),
                        rs.getInt("codigo_produto"),
                        rs.getString("produto"),
                        rs.getDouble("valor_pedido"),
                        rs.getString("categoria"),
                        rs.getString("cnpj_fornecedor"),
                        rs.getString("fornecedor_nome_fantasia"),
                        rs.getString("razao_social_fornecedor")
                        ));
    }


    private String exportarCsvCompleto(Integer usuarioLogadoId) {
        return "SELECT " +
                " c.NOME  AS  nome_vendedor , " +
                " c.CPF  AS  cpf_vendedor, " +
                " c.EMAIL  AS  email_vendedor , " +
                " CONCAT(c.RUA " +
                " , ' - nº ', c.NUMERO)AS  endereco_vendedor , " +
                " c.CIDADE  AS  cidade , " +
                " es.ESTADO  AS  estado , " +
                " r.NOME  AS  regiao , " +
                " u.NOME  AS  usuario_vendedor ," +
                " v.ID  AS  codigo_venda , " +
                " pv.QUANTIDADE " +
                " AS  quantidade_itens , " +
                " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)) as data_venda, " +
                " v.SITUACAO  AS  situacao_venda , " +
                " v.aprovacao  AS  aprovacao_venda , " +
                " v.cliente_nome as cliente_nome, " +
                " v.cliente_email as cliente_email, " +
                " v.cliente_cpf as cliente_cpf, " +
                " p.ID  AS  codigo_produto , " +
                " p.NOME_PRODUTO AS  produto , " +
                " p.PRECO  AS  valor_pedido , " +
                " ct.DESCRICAO AS  categoria , " +
                " f.CNPJ  AS  cnpj_fornecedor , " +
                " f.NOME_FANTASIA AS  fornecedor_nome_fantasia , " +
                " f.RAZAO_SOCIAL AS  razao_social_fornecedor "  +
                "FROM Vendedor c " +
                " LEFT JOIN Usuario u ON c.id = u.vendedor_id " +
                " LEFT JOIN Estado es ON es.id = c.estado_id " +
                " LEFT JOIN Regiao r ON es.regiao_id = r.id " +
                " LEFT JOIN Venda v ON v.vendedor_id = c.id " +
                " LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id " +
                " LEFT JOIN Produto p ON p.id = pv.produto_id " +
                " LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id " +
                " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id " +
                " WHERE u.id = " + usuarioLogadoId +
                " UNION " +
                "SELECT " +
                " c.NOME  AS  nome_vendedor , " +
                " c.CPF  AS  cpf_vendedor, " +
                " c.EMAIL  AS  email_vendedor , " +
                " CONCAT(c.RUA " +
                " , ' - nº ', c.NUMERO)AS  endereco_vendedor , " +
                " c.CIDADE  AS  cidade , " +
                " es.ESTADO  AS  estado , " +
                " r.NOME  AS  regiao , " +
                " u.NOME  AS  usuario_vendedor ," +
                " v.ID  AS  codigo_venda , " +
                " pv.QUANTIDADE " +
                " AS  quantidade_itens , " +
                " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)) as data_venda, " +
                " v.SITUACAO  AS  situacao_venda , " +
                " v.aprovacao  AS  aprovacao_venda , " +
                " v.cliente_nome as cliente_nome, " +
                " v.cliente_email as cliente_email, " +
                " v.cliente_cpf as cliente_cpf, " +
                " p.ID  AS  codigo_produto , " +
                " p.NOME_PRODUTO AS  produto , " +
                " p.PRECO  AS  valor_pedido , " +
                " ct.DESCRICAO AS  categoria , " +
                " f.CNPJ  AS  cnpj_fornecedor , " +
                " f.NOME_FANTASIA AS  fornecedor_nome_fantasia , " +
                " f.RAZAO_SOCIAL AS  razao_social_fornecedor "  +
                "FROM Vendedor c " +
                " LEFT JOIN Usuario u ON c.id = u.vendedor_id " +
                " LEFT JOIN Estado es ON es.id = c.estado_id " +
                " LEFT JOIN Regiao r ON es.regiao_id = r.id " +
                " LEFT JOIN Venda v ON v.vendedor_id = c.id " +
                " LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id " +
                " LEFT JOIN Produto p ON p.id = pv.produto_id " +
                " LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id " +
                " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id " +
                " WHERE u.usuario_proprietario = " + usuarioLogadoId;
    }

    public List<ExportarCsvDto> exportarCsvSemFiltroDeData(Integer usuarioLogadoId) {
        return jdbcTemplate.query(exportarCsvCompleto(usuarioLogadoId),
                (rs, rowNum) -> new ExportarCsvDto(
                        rs.getString("nome_vendedor"),
                        rs.getString("cpf_vendedor"),
                        rs.getString("email_vendedor"),
                        rs.getString("endereco_vendedor"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("regiao"),
                        rs.getString("usuario_vendedor"),
                        rs.getInt("codigo_venda"),
                        rs.getInt("quantidade_itens"),
                        rs.getString("data_venda"),
                        rs.getString("situacao_venda"),
                        rs.getString("aprovacao_venda"),
                        rs.getString("cliente_nome"),
                        rs.getString("cliente_email"),
                        rs.getString("cliente_cpf"),
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
