package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.model.RelatoriosPowerBi;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PowerBiRepository extends JpaRepository<RelatoriosPowerBi, Integer> {

    List<RelatoriosPowerBi> findByUsuario(Usuario usuario);
}
