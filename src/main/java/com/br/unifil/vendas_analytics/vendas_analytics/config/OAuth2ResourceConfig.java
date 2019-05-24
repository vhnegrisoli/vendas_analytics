package com.br.unifil.vendas_analytics.vendas_analytics.config;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.PermissoesUsuarioEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.PermissoesUsuarioEnum.ADMIN;
import static com.br.unifil.vendas_analytics.vendas_analytics.enums.PermissoesUsuarioEnum.USER;

@Configuration
@EnableResourceServer
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] permitAll = {
                "/login/**",
        };

        http
                .addFilterBefore(new CorsConfigFilter(), ChannelProcessingFilter.class)
                .requestMatchers()
                .antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(permitAll).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/oauth/token").hasAnyRole(ADMIN.name(), USER.name())
                .antMatchers("/oauth/authorize").hasAnyRole(ADMIN.name(), USER.name())
                .antMatchers("/api/clientes/**").hasAnyRole(ADMIN.name(), USER.name())
                .antMatchers("/api/vendas/**").hasAnyRole(ADMIN.name(), USER.name())
                .antMatchers("/api/produtos/**").hasAnyRole(ADMIN.name(), USER.name())
                .antMatchers("/api/categorias/**").hasRole(ADMIN.name())
                .antMatchers("/api/fornecedores/**").hasRole(ADMIN.name())
                .antMatchers("/api/analytics/**").hasAnyRole(ADMIN.name(), USER.name())
                .antMatchers("/api/dashboard/**").hasAnyRole(ADMIN.name(), USER.name())
                .antMatchers("/api/estados/**").hasAnyRole(ADMIN.name(), USER.name())
                .antMatchers("/api/dashboard/**").hasAnyRole(ADMIN.name(), USER.name())
                .antMatchers("/api/usuarios/*").hasRole(ADMIN.name())
                .antMatchers("/api/relatorios-power-bi/*").hasRole(ADMIN.name())
                .anyRequest().authenticated();
    }

}
