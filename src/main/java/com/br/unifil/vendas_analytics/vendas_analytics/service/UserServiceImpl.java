package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage.UsuarioExceptionMessage.USUARIO_NAO_ENCONTRADO;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;

@Service(value = "userService")
public class UserServiceImpl extends UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario usuario = usuarioRepository.findByEmailAndSituacao(email, ATIVO)
            .orElseThrow(USUARIO_NAO_ENCONTRADO::getException);
        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getSenha(), getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public List<Usuario> findAll() {
        List<Usuario> list = new ArrayList<>();
        usuarioRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }
}