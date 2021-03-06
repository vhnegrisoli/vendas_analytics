package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.dto.UsuarioAlteracaoSenhaDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.PermissoesUsuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.PermissoesUsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PermissoesUsuarioRepository permissoesUsuarioRepository;

    @GetMapping("/todos")
    public List<Usuario> getAll() {
        return usuarioService.buscarTodos();
    }

    @PostMapping("/salvar")
    public void save(@RequestBody Usuario usuario) {
        usuarioService.salvarUsuario(usuario);
    }

    @GetMapping("buscar/{id}")
    public Usuario findOne(@PathVariable Integer id) {
        return usuarioService.buscarUm(id);
    }

    @GetMapping("/permissoes")
    public List<PermissoesUsuario> getAllPermissoes() {
        return permissoesUsuarioRepository.findAll();
    }

    @GetMapping("/remover/{id}")
    public void remover(@PathVariable Integer id) {
        usuarioService.removerUsuario(id);
    }

    @GetMapping("/buscar-administradores")
    public List<Usuario> buscarAdmins() {
        return usuarioService.buscarAdministradores();
    }

    @GetMapping("/atualizar-ultimo-acesso/{id}")
    public Usuario atualizarUltimoAcesso(@PathVariable Integer id) {
        return usuarioService.atualizarUltimoAcesso(id);
    }

    @PutMapping("/atualizar-senha")
    public void atualizarUltimoAcesso(@RequestBody UsuarioAlteracaoSenhaDto usuarioAlteracaoSenhaDto) {
        usuarioService.alterarSenhaUsuario(usuarioAlteracaoSenhaDto);
    }

}
