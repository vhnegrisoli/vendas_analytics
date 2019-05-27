package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao;
import com.br.unifil.vendas_analytics.vendas_analytics.model.PermissoesUsuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByVendedorId(Integer id);

    Optional<Usuario> findByVendedorIdAndSituacao(Integer id, UsuarioSituacao situacao);

    Optional<Usuario> findByEmailAndSituacao(String email, UsuarioSituacao situacao);

    List<Usuario> findByPermissoesUsuarioAndSituacao(PermissoesUsuario permissoesUsuario,
                                                     UsuarioSituacao usuarioSituacao);
}
