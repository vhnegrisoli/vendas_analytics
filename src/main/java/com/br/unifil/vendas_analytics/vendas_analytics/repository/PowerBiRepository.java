package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.model.RelatoriosPowerBi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PowerBiRepository extends JpaRepository<RelatoriosPowerBi, Integer> {
}
