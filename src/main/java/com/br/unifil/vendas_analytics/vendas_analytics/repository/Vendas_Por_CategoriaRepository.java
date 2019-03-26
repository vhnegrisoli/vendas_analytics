package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard.vendas_por_categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Vendas_Por_CategoriaRepository extends JpaRepository<vendas_por_categoria, Integer> {
}
