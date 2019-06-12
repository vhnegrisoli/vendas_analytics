package com.br.unifil.vendas_analytics.vendas_analytics.controller;

import com.br.unifil.vendas_analytics.vendas_analytics.model.PermissoesUsuario;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Usuario;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.PermissoesUsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.UsuarioRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.service.UsuarioService;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao.ATIVO;

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
    public void save(@RequestBody Usuario usuario) throws ValidacaoException {
        usuarioService.salvarUsuario(usuario);
    }

    @GetMapping("buscar/{id}")
    public Usuario findOne(@PathVariable Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ValidacaoException("Usuário não encontrado"));
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
        PermissoesUsuario permissoesUsuario = new PermissoesUsuario();
        permissoesUsuario.setId(2);
        return usuarioRepository.findByPermissoesUsuarioAndSituacao(permissoesUsuario, ATIVO);
    }

}
