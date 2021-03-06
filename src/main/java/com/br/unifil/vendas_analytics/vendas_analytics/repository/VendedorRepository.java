package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {

    Optional<Vendedor> findByCpf(String cpf);

    Optional<Vendedor> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByCpf(String cpf);

    List<Vendedor> findByIdIn(List<Integer> ids);

}
