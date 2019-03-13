package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.PermissoesUsuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.PermissoesUsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.service.UsuarioService;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PermissoesUsuarioRepository permissoesUsuarioRepository;

    @GetMapping("/todos")
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @PostMapping("/salvar")
    public void save(@RequestBody Usuario usuario) throws ValidacaoException {
        usuarioService.salvarUsuario(usuario);
    }

    @GetMapping("/permissoes")
    public List<PermissoesUsuario> getAllPermissoes() {
        return permissoesUsuarioRepository.findAll();
    }

}
