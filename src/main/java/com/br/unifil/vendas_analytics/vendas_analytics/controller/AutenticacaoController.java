package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autenticacao")
public class AutenticacaoController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario-logado")
    public UsuarioAutenticadoDto nomeUsuarioLogado() {
        return usuarioService.getUsuarioLogado();
    }

    @GetMapping("/complete-login")
    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

