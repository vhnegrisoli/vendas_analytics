package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.model.HistoricoVenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoVendaRepository extends JpaRepository<HistoricoVenda, Integer> {
}
