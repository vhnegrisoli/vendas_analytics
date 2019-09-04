package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.ExportarCsvDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings({"checkstyle:methodlength"})
public class ExportarCsvRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
        EXPORTAR CSV USER/ADMIN COM FILTRO DE DATA
     */

    private String exportarCsvFiltroData(String dataInicial, String dataFinal, Integer usuarioLogadoId) {
        return "SELECT "
            + " c.NOME  AS  nomeVendedor , "
            + " c.CPF  AS  cpfVendedor, "
            + " c.EMAIL  AS  emailVendedor , "
            + " CONCAT(c.RUA "
            + " , ' - nº ', c.NUMERO)AS  enderecoVendedor , "
            + " c.CIDADE  AS  cidade , "
            + " es.ESTADO  AS  estado , "
            + " r.NOME  AS  regiao , "
            + " u.NOME  AS  usuarioVendedor ,"
            + " v.ID  AS  codigoVenda , "
            + " pv.QUANTIDADE "
            + " AS  quantidadeItens , "
            + " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)) AS dataVenda, "
            + " v.SITUACAO  AS  situacaoVenda , "
            + " v.aprovacao  AS  aprovacaoVenda , "
            + " v.cliente_nome AS clienteNome, "
            + " v.cliente_email AS clienteEmail, "
            + " v.cliente_cpf AS clienteCpf, "
            + " p.ID  AS  codigoProduto , "
            + " p.NOME_PRODUTO AS  produto , "
            + " p.PRECO  AS  valorPedido , "
            + " ct.DESCRICAO AS  categoria , "
            + " f.CNPJ  AS  cnpjFornecedor , "
            + " f.NOME_FANTASIA AS  fornecedorNomeFantasia , "
            + " f.RAZAO_SOCIAL AS  razaoSocialFornecedor "
            + "FROM Vendedor c "
            + " LEFT JOIN Usuario u ON c.id = u.vendedor_id "
            + " LEFT JOIN Estado es ON es.id = c.estado_id "
            + " LEFT JOIN Regiao r ON es.regiao_id = r.id "
            + " LEFT JOIN Venda v ON v.vendedor_id = c.id "
            + " LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id "
            + " LEFT JOIN Produto p ON p.id = pv.produto_id "
            + " LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id "
            + " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id "
            + " WHERE v.DATA_COMPRA BETWEEN CAST('" + dataInicial + "' AS DATE) AND  CAST('" + dataFinal + "' AS DATE)"
            + " AND u.id = " + usuarioLogadoId
            + " UNION "
            + " SELECT "
            + " c.NOME  AS  nomeVendedor , "
            + " c.CPF  AS  cpfVendedor, "
            + " c.EMAIL  AS  emailVendedor , "
            + " CONCAT(c.RUA "
            + " , ' - nº ', c.NUMERO)AS  enderecoVendedor , "
            + " c.CIDADE  AS  cidade , "
            + " es.ESTADO  AS  estado , "
            + " r.NOME  AS  regiao , "
            + " u.NOME  AS  usuarioVendedor ,"
            + " v.ID  AS  codigoVenda , "
            + " pv.QUANTIDADE "
            + " AS  quantidadeItens , "
            + " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)) AS dataVenda, "
            + " v.SITUACAO  AS  situacaoVenda , "
            + " v.aprovacao  AS  aprovacaoVenda , "
            + " v.cliente_nome AS clienteNome, "
            + " v.cliente_email AS clienteEmail, "
            + " v.cliente_cpf AS clienteCpf, "
            + " p.ID  AS  codigoProduto , "
            + " p.NOME_PRODUTO AS  produto , "
            + " p.PRECO  AS  valorPedido , "
            + " ct.DESCRICAO AS  categoria , "
            + " f.CNPJ  AS  cnpjFornecedor , "
            + " f.NOME_FANTASIA AS  fornecedorNomeFantasia , "
            + " f.RAZAO_SOCIAL AS  razaoSocialFornecedor "
            + "FROM Vendedor c "
            + " LEFT JOIN Usuario u ON c.id = u.vendedor_id "
            + " LEFT JOIN Estado es ON es.id = c.estado_id "
            + " LEFT JOIN Regiao r ON es.regiao_id = r.id "
            + " LEFT JOIN Venda v ON v.vendedor_id = c.id "
            + " LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id "
            + " LEFT JOIN Produto p ON p.id = pv.produto_id "
            + " LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id "
            + " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id "
            + " WHERE v.DATA_COMPRA BETWEEN CAST('" + dataInicial + "' AS DATE) AND  CAST('" + dataFinal + "' AS DATE)"
            + " AND u.usuario_proprietario = " + usuarioLogadoId;
    }

    public List<ExportarCsvDto> exportarCsvComFiltroDeData(String dataInicial,
                                                           String dataFinal,
                                                           Integer usuarioLogadoId) {
        return jdbcTemplate.query(exportarCsvFiltroData(dataInicial, dataFinal, usuarioLogadoId),
            (rs, rowNum) -> new ExportarCsvDto(
                rs.getString("nomeVendedor"),
                rs.getString("cpfVendedor"),
                rs.getString("emailVendedor"),
                rs.getString("enderecoVendedor"),
                rs.getString("cidade"),
                rs.getString("estado"),
                rs.getString("regiao"),
                rs.getString("usuarioVendedor"),
                rs.getInt("codigoVenda"),
                rs.getInt("quantidadeItens"),
                rs.getString("dataVenda"),
                rs.getString("situacaoVenda"),
                rs.getString("aprovacaoVenda"),
                rs.getString("clienteNome"),
                rs.getString("clienteEmail"),
                rs.getString("clienteCpf"),
                rs.getInt("codigoProduto"),
                rs.getString("produto"),
                rs.getDouble("valorPedido"),
                rs.getString("categoria"),
                rs.getString("cnpjFornecedor"),
                rs.getString("fornecedorNomeFantasia"),
                rs.getString("razaoSocialFornecedor")
            ));
    }

    /*
        EXPORTAR CSV USER/ADMIN COMPLETO (SEM FILTRO DE DATA)
     */

    private String exportarCsvCompleto(Integer usuarioLogadoId) {
        return "SELECT "
            + " c.NOME  AS  nomeVendedor , "
            + " c.CPF  AS  cpfVendedor, "
            + " c.EMAIL  AS  emailVendedor , "
            + " CONCAT(c.RUA "
            + " , ' - nº ', c.NUMERO)AS  enderecoVendedor , "
            + " c.CIDADE  AS  cidade , "
            + " es.ESTADO  AS  estado , "
            + " r.NOME  AS  regiao , "
            + " u.NOME  AS  usuarioVendedor ,"
            + " v.ID  AS  codigoVenda , "
            + " pv.QUANTIDADE "
            + " AS  quantidadeItens , "
            + " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)) AS dataVenda, "
            + " v.SITUACAO  AS  situacaoVenda , "
            + " v.aprovacao  AS  aprovacaoVenda , "
            + " v.cliente_nome AS clienteNome, "
            + " v.cliente_email AS clienteEmail, "
            + " v.cliente_cpf AS clienteCpf, "
            + " p.ID  AS  codigoProduto , "
            + " p.NOME_PRODUTO AS  produto , "
            + " p.PRECO  AS  valorPedido , "
            + " ct.DESCRICAO AS  categoria , "
            + " f.CNPJ  AS  cnpjFornecedor , "
            + " f.NOME_FANTASIA AS  fornecedorNomeFantasia , "
            + " f.RAZAO_SOCIAL AS  razaoSocialFornecedor "
            + "FROM Vendedor c "
            + " LEFT JOIN Usuario u ON c.id = u.vendedor_id "
            + " LEFT JOIN Estado es ON es.id = c.estado_id "
            + " LEFT JOIN Regiao r ON es.regiao_id = r.id "
            + " LEFT JOIN Venda v ON v.vendedor_id = c.id "
            + " LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id "
            + " LEFT JOIN Produto p ON p.id = pv.produto_id "
            + " LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id "
            + " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id "
            + " WHERE u.id = " + usuarioLogadoId
            + " UNION "
            + "SELECT "
            + " c.NOME  AS  nomeVendedor , "
            + " c.CPF  AS  cpfVendedor, "
            + " c.EMAIL  AS  emailVendedor , "
            + " CONCAT(c.RUA "
            + " , ' - nº ', c.NUMERO)AS  enderecoVendedor , "
            + " c.CIDADE  AS  cidade , "
            + " es.ESTADO  AS  estado , "
            + " r.NOME  AS  regiao , "
            + " u.NOME  AS  usuarioVendedor ,"
            + " v.ID  AS  codigoVenda , "
            + " pv.QUANTIDADE "
            + " AS  quantidadeItens , "
            + " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)) AS dataVenda, "
            + " v.SITUACAO  AS  situacaoVenda , "
            + " v.aprovacao  AS  aprovacaoVenda , "
            + " v.cliente_nome AS clienteNome, "
            + " v.cliente_email AS clienteEmail, "
            + " v.cliente_cpf AS clienteCpf, "
            + " p.ID  AS  codigoProduto , "
            + " p.NOME_PRODUTO AS  produto , "
            + " p.PRECO  AS  valorPedido , "
            + " ct.DESCRICAO AS  categoria , "
            + " f.CNPJ  AS  cnpjFornecedor , "
            + " f.NOME_FANTASIA AS  fornecedorNomeFantasia , "
            + " f.RAZAO_SOCIAL AS  razaoSocialFornecedor "
            + "FROM Vendedor c "
            + " LEFT JOIN Usuario u ON c.id = u.vendedor_id "
            + " LEFT JOIN Estado es ON es.id = c.estado_id "
            + " LEFT JOIN Regiao r ON es.regiao_id = r.id "
            + " LEFT JOIN Venda v ON v.vendedor_id = c.id "
            + " LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id "
            + " LEFT JOIN Produto p ON p.id = pv.produto_id "
            + " LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id "
            + " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id "
            + " WHERE u.usuario_proprietario = " + usuarioLogadoId;
    }

    public List<ExportarCsvDto> exportarCsvSemFiltroDeData(Integer usuarioLogadoId) {
        return jdbcTemplate.query(exportarCsvCompleto(usuarioLogadoId),
            (rs, rowNum) -> new ExportarCsvDto(
                rs.getString("nomeVendedor"),
                rs.getString("cpfVendedor"),
                rs.getString("emailVendedor"),
                rs.getString("enderecoVendedor"),
                rs.getString("cidade"),
                rs.getString("estado"),
                rs.getString("regiao"),
                rs.getString("usuarioVendedor"),
                rs.getInt("codigoVenda"),
                rs.getInt("quantidadeItens"),
                rs.getString("dataVenda"),
                rs.getString("situacaoVenda"),
                rs.getString("aprovacaoVenda"),
                rs.getString("clienteNome"),
                rs.getString("clienteEmail"),
                rs.getString("clienteCpf"),
                rs.getInt("codigoProduto"),
                rs.getString("produto"),
                rs.getDouble("valorPedido"),
                rs.getString("categoria"),
                rs.getString("cnpjFornecedor"),
                rs.getString("fornecedorNomeFantasia"),
                rs.getString("razaoSocialFornecedor")
            ));
    }

    /*
        EXPORTAR CSV SUPER_ADMIN COM FILTRO DE DATA
     */

    private String exportarCsvSuperAdminFiltroData(String dataInicial, String dataFinal) {
        return "SELECT "
            + " c.NOME  AS  nomeVendedor , "
            + " c.CPF  AS  cpfVendedor, "
            + " c.EMAIL  AS  emailVendedor , "
            + " CONCAT(c.RUA "
            + " , ' - nº ', c.NUMERO)AS  enderecoVendedor , "
            + " c.CIDADE  AS  cidade , "
            + " es.ESTADO  AS  estado , "
            + " r.NOME  AS  regiao , "
            + " u.NOME  AS  usuarioVendedor ,"
            + " v.ID  AS  codigoVenda , "
            + " pv.QUANTIDADE "
            + " AS  quantidadeItens , "
            + " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)) AS dataVenda, "
            + " v.SITUACAO  AS  situacaoVenda , "
            + " v.aprovacao  AS  aprovacaoVenda , "
            + " v.cliente_nome AS clienteNome, "
            + " v.cliente_email AS clienteEmail, "
            + " v.cliente_cpf AS clienteCpf, "
            + " p.ID  AS  codigoProduto , "
            + " p.NOME_PRODUTO AS  produto , "
            + " p.PRECO  AS  valorPedido , "
            + " ct.DESCRICAO AS  categoria , "
            + " f.CNPJ  AS  cnpjFornecedor , "
            + " f.NOME_FANTASIA AS  fornecedorNomeFantasia , "
            + " f.RAZAO_SOCIAL AS  razaoSocialFornecedor "
            + "FROM Vendedor c "
            + " LEFT JOIN Usuario u ON c.id = u.vendedor_id "
            + " LEFT JOIN Estado es ON es.id = c.estado_id "
            + " LEFT JOIN Regiao r ON es.regiao_id = r.id "
            + " LEFT JOIN Venda v ON v.vendedor_id = c.id "
            + " LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id "
            + " LEFT JOIN Produto p ON p.id = pv.produto_id "
            + " LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id "
            + " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id "
            + " WHERE v.DATA_COMPRA BETWEEN CAST('" + dataInicial + "' AS DATE) AND  CAST('" + dataFinal + "' AS DATE)";
    }

    public List<ExportarCsvDto> exportarCsvSuperAdminFiltros(String dataInicial,
                                                             String dataFinal) {
        return jdbcTemplate.query(exportarCsvSuperAdminFiltroData(dataInicial, dataFinal),
            (rs, rowNum) -> new ExportarCsvDto(
                rs.getString("nomeVendedor"),
                rs.getString("cpfVendedor"),
                rs.getString("emailVendedor"),
                rs.getString("enderecoVendedor"),
                rs.getString("cidade"),
                rs.getString("estado"),
                rs.getString("regiao"),
                rs.getString("usuarioVendedor"),
                rs.getInt("codigoVenda"),
                rs.getInt("quantidadeItens"),
                rs.getString("dataVenda"),
                rs.getString("situacaoVenda"),
                rs.getString("aprovacaoVenda"),
                rs.getString("clienteNome"),
                rs.getString("clienteEmail"),
                rs.getString("clienteCpf"),
                rs.getInt("codigoProduto"),
                rs.getString("produto"),
                rs.getDouble("valorPedido"),
                rs.getString("categoria"),
                rs.getString("cnpjFornecedor"),
                rs.getString("fornecedorNomeFantasia"),
                rs.getString("razaoSocialFornecedor")
            ));
    }

    /*
        EXPORTAR CSV SUPER ADMIN COMPLETO (SEM FILTRO DE DATA)
     */

    private String exportarCsvCompletoSuperAdmin() {
        return "SELECT "
            + " c.NOME  AS  nomeVendedor , "
            + " c.CPF  AS  cpfVendedor, "
            + " c.EMAIL  AS  emailVendedor , "
            + " CONCAT(c.RUA "
            + " , ' - nº ', c.NUMERO)AS  enderecoVendedor , "
            + " c.CIDADE  AS  cidade , "
            + " es.ESTADO  AS  estado , "
            + " r.NOME  AS  regiao , "
            + " u.NOME  AS  usuarioVendedor ,"
            + " v.ID  AS  codigoVenda , "
            + " pv.QUANTIDADE "
            + " AS  quantidadeItens , "
            + " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)) AS dataVenda, "
            + " v.SITUACAO  AS  situacaoVenda , "
            + " v.aprovacao  AS  aprovacaoVenda , "
            + " v.cliente_nome AS clienteNome, "
            + " v.cliente_email AS clienteEmail, "
            + " v.cliente_cpf AS clienteCpf, "
            + " p.ID  AS  codigoProduto , "
            + " p.NOME_PRODUTO AS  produto , "
            + " p.PRECO  AS  valorPedido , "
            + " ct.DESCRICAO AS  categoria , "
            + " f.CNPJ  AS  cnpjFornecedor , "
            + " f.NOME_FANTASIA AS  fornecedorNomeFantasia , "
            + " f.RAZAO_SOCIAL AS  razaoSocialFornecedor "
            + "FROM Vendedor c "
            + " LEFT JOIN Usuario u ON c.id = u.vendedor_id "
            + " LEFT JOIN Estado es ON es.id = c.estado_id "
            + " LEFT JOIN Regiao r ON es.regiao_id = r.id "
            + " LEFT JOIN Venda v ON v.vendedor_id = c.id "
            + " LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id "
            + " LEFT JOIN Produto p ON p.id = pv.produto_id "
            + " LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id "
            + " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id ";
    }

    public List<ExportarCsvDto> exportarCsvSuperAdminCompleto() {
        return jdbcTemplate.query(exportarCsvCompletoSuperAdmin(),
            (rs, rowNum) -> new ExportarCsvDto(
                rs.getString("nomeVendedor"),
                rs.getString("cpfVendedor"),
                rs.getString("emailVendedor"),
                rs.getString("enderecoVendedor"),
                rs.getString("cidade"),
                rs.getString("estado"),
                rs.getString("regiao"),
                rs.getString("usuarioVendedor"),
                rs.getInt("codigoVenda"),
                rs.getInt("quantidadeItens"),
                rs.getString("dataVenda"),
                rs.getString("situacaoVenda"),
                rs.getString("aprovacaoVenda"),
                rs.getString("clienteNome"),
                rs.getString("clienteEmail"),
                rs.getString("clienteCpf"),
                rs.getInt("codigoProduto"),
                rs.getString("produto"),
                rs.getDouble("valorPedido"),
                rs.getString("categoria"),
                rs.getString("cnpjFornecedor"),
                rs.getString("fornecedorNomeFantasia"),
                rs.getString("razaoSocialFornecedor")
            ));
    }
}