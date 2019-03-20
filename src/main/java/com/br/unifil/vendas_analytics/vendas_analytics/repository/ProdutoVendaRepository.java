package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.model.ProdutoVenda;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoVendaRepository extends JpaRepository<ProdutoVenda, Integer> {

    List<ProdutoVenda> findByVendaId(Venda venda);

}
