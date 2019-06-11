package com.br.unifil.vendas_analytics.vendas_analytics.config.auth;

import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();
//        Usuario usuario = usuarioRepository.findByEmailAndSituacao(user.getUsername(), ATIVO)
//            .orElseThrow(() -> new ValidacaoException("Usuário não encontrado"));
//        additionalInfo.put("id", usuario.getId());
//        additionalInfo.put("authorities", usuario.getPermissoesUsuario());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }

}