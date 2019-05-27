package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaAprovacaoEnum;
import com.br.unifil.vendas_analytics.vendas_analytics.enums.VendaSituacaoEnum;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Vendedor;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {

    Integer countBySituacaoAndAprovacao(VendaSituacaoEnum situacao, VendaAprovacaoEnum aprovacao);

    Integer countByAprovacaoNot(VendaAprovacaoEnum aprovacao);

    @Query(value = "SELECT p.id as id, " +
            "p.nome_produto as produto, " +
            "p.descricao as descricao, " +
            "f.razao_social as fornecedor, " +
            "c.descricao as categoria ," +
            "p.preco as preco, " +
            "pv.quantidade as quantidade " +
            "FROM Produto p " +
            "LEFT JOIN Produto_Venda pv ON p.id = pv.produto_id " +
            "LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id " +
            "LEFT JOIN Categoria c ON c.id = p.categoria_id " +
            "WHERE pv.venda_id = ?1",
            nativeQuery = true)
    List<Object[]> findAllProdutosDaVendaByVendaId(int id);

    List<Venda> findByClientes(Vendedor vendedor);
}
