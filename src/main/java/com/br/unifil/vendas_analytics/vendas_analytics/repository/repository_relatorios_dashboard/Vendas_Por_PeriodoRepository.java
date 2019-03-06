package com.br.unifil.vendas_analytics.vendas_analytics.repository.repository_relatorios_dashboard;

import com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard.vendas_por_periodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Vendas_Por_PeriodoRepository extends JpaRepository<vendas_por_periodo, Integer> {
}
