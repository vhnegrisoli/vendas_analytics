package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;

@Service(value = "userService")
public class UserServiceImpl extends UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailAndSituacao(email, ATIVO)
            .orElseThrow(() -> new ValidacaoException(""));
        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getSenha(), getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public List<Usuario> findAll() {
        List<Usuario> list = new ArrayList<>();
        usuarioRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }
}