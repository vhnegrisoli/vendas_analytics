package com.br.unifil.vendas_analytics.vendas_analytics.config.auth;

import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage.UsuarioExceptionMessage.USUARIO_ACESSO_INVALIDO;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return usuarioRepository
            .findByEmailAndSituacao(email, ATIVO)
            .map(usuario -> {
                List<GrantedAuthority> permissoes = AuthorityUtils
                    .createAuthorityList("ROLE_" + usuario.getPermissoesUsuario().getPermissao().name());
                return new User(
                    usuario.getEmail(),
                    usuario.getSenha(),
                    permissoes);
            }).orElseThrow(USUARIO_ACESSO_INVALIDO::getException);
    }
}