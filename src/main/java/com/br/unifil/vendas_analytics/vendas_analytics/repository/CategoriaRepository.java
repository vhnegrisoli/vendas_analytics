package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
