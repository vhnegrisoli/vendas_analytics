package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    Optional<Endereco> findByRuaAndNumeroAndCep(String rua, int numero, String cep);
}
