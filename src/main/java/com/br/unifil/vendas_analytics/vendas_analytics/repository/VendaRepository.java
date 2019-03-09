package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.ExportarCsvDto;
import com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum;
import com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {

    @Query(value = "SELECT " +
            "ROW_NUMBER() OVER (ORDER BY v.data_compra) AS id, " +
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
            " v.QUANTIDADE_DE_ITENS " +
            " AS  quantidade_itens , " +
            " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)), " +
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
            " WHERE v.DATA_COMPRA BETWEEN ?1 AND  ?2", nativeQuery = true)
    List<Object> getRelatoriosCsv(String dataInicial, String dataFinal);

    @Query(value = "SELECT " +
            "ROW_NUMBER() OVER (ORDER BY v.data_compra) AS id, " +
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
            " v.QUANTIDADE_DE_ITENS " +
            " AS  quantidade_itens , " +
            " CONCAT(DAY(v.DATA_COMPRA),'/',MONTH(v.DATA_COMPRA),'/',YEAR(v.DATA_COMPRA)), " +
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
            " LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id ", nativeQuery = true)
    List<Object> getRelatoriosCsvGeral();

    List<Venda> findBySituacaoAndAprovacao(VendaSituacaoEnum situacao, VendaAprovacaoEnum aprovacao);

    List<Venda> findBySituacaoNot(VendaSituacaoEnum situacao);

}
