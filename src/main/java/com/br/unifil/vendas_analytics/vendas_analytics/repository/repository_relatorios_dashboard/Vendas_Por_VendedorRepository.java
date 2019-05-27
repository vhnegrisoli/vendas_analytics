package com.br.unifil.vendas_analytics.vendas_analytics.repository.repository_relatorios_dashboard;

import com.br.unifil.vendas_analytics.vendas_analytics.model.model_relatorios_dashboard.vendas_por_cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Vendas_Por_VendedorRepository extends JpaRepository<vendas_por_cliente, Integer> {
}
