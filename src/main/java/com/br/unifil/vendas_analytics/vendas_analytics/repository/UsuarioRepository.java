package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByClienteId(Integer id);

    Optional<Usuario> findByClienteIdAndSituacao(Integer id, UsuarioSituacao situacao);

    Optional<Usuario> findByEmailAndSituacao(String email, UsuarioSituacao situacao);

}
