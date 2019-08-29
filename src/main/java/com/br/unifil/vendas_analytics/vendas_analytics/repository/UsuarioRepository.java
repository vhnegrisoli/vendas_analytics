package com.br.unifil.vendas_analytics.vendas_analytics.repository;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao;
import com.br.unifil.vendas_analytics.vendas_analytics.model.PermissoesUsuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByVendedorId(Integer id);

    Optional<Usuario> findByVendedorIdAndSituacao(Integer id, UsuarioSituacao situacao);

    Optional<Usuario> findByEmailAndSituacao(String email, UsuarioSituacao situacao);

    List<Usuario> findByPermissoesUsuarioAndSituacao(PermissoesUsuario permissoesUsuario,
                                                           UsuarioSituacao usuarioSituacao);

    List<Usuario> findByPermissoesUsuarioInAndSituacao(List<PermissoesUsuario> permissoesUsuario,
                                                     UsuarioSituacao usuarioSituacao);

    @Modifying
    @Query(value = "update Usuario a set a.ultimoAcesso = :ultimoAcesso where a.id = :id")
    void atualizarUltimoAcessoUsuario(LocalDateTime ultimoAcesso, Integer id);

    @Modifying
    @Query(value = "update Usuario a set a.senha = :senha where a.id = :id")
    void atualizarSenha(String senha, Integer id);
}
