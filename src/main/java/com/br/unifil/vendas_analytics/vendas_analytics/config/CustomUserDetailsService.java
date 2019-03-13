//package com.br.unifil.vendas_analytics.vendas_analytics.config;
//
//import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
//import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
//import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
//import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;
//
//@Service("customUserDetailsService")
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Usuario usuario = usuarioRepository
//                .findByEmailAndSituacao(email, ATIVO)
//            .orElseThrow(() -> new ValidacaoException("Usuário não encontrado"));
//        List<GrantedAuthority> authorityListAdmin = AuthorityUtils
//                .createAuthorityList("ROLE_" + usuario.getPermissoesUsuario().getPermissao().name());
//        return new User(
//                usuario.getEmail(),
//                usuario.getSenha(),
//                authorityListAdmin);
//    }
//}
