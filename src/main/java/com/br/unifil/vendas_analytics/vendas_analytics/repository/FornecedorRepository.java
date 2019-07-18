package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {

    Optional<Fornecedor> findByIdAndUsuarioCadastroIn(Integer id, List<Integer> usuariosCadastro);

    List<Fornecedor> findByUsuarioCadastroIn(List<Integer> usuariosCadastro);

}
