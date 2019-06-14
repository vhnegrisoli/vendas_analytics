package com.br.unifil.vendas_analytics.vendas_analytics.config.auth;

import com.br.unifil.vendas_analytics.vendas_analytics.config.CorsConfigFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.PermissoesUsuarioEnum.*;

@Configuration
@EnableResourceServer
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] permitAll = {
                "/login/**",
                "/oauth/token",
                "/oauth/authorize",
        };

        http
                .addFilterBefore(new CorsConfigFilter(), ChannelProcessingFilter.class)
                .requestMatchers()
                .antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(permitAll).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/autenticacao/**").hasAnyRole(SUPER_ADMIN.name(), ADMIN.name(), USER.name())
                .antMatchers("/api/vendedores/**").hasAnyRole(SUPER_ADMIN.name(), ADMIN.name(), USER.name())
                .antMatchers("/api/vendas/**").hasAnyRole(SUPER_ADMIN.name(), ADMIN.name(), USER.name())
                .antMatchers("/api/produtos/**").hasAnyRole(SUPER_ADMIN.name(), ADMIN.name(), USER.name(), USER.name())
                .antMatchers("/api/categorias/**").hasAnyRole(SUPER_ADMIN.name(), ADMIN.name(), USER.name())
                .antMatchers("/api/fornecedores/**").hasAnyRole(SUPER_ADMIN.name(), ADMIN.name(), USER.name())
                .antMatchers("/api/analytics/**").hasAnyRole(SUPER_ADMIN.name(), ADMIN.name(), USER.name())
                .antMatchers("/api/dashboard/**").hasAnyRole(SUPER_ADMIN.name(), ADMIN.name(), USER.name())
                .antMatchers("/api/estados/**").hasAnyRole(SUPER_ADMIN.name(), ADMIN.name(), USER.name())
                .antMatchers("/api/dashboard/**").hasAnyRole(SUPER_ADMIN.name(), ADMIN.name(), USER.name())
                .antMatchers("/api/usuarios/**").hasAnyRole(SUPER_ADMIN.name(), ADMIN.name())
                .antMatchers("/api/relatorios-power-bi/**").hasAnyRole(SUPER_ADMIN.name(), ADMIN.name())
                .anyRequest().authenticated();
    }
}
