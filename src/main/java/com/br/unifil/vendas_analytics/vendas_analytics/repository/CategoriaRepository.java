package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Optional<Categoria> findByIdAndUsuarioCadastroIn(Integer id, List<Integer> usuariosCadastro);

    List<Categoria> findByUsuarioCadastroIn(List<Integer> usuariosCadastro);
}
