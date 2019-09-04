package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Categoria;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.CategoriaRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage.ProdutoExceptionMessage.CATEGORIA_NAO_ENCONTRADA;
import static com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage.ProdutoExceptionMessage.CATEGORIA_VINCULADA_PRODUTO;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoRepository produtoRepository;

    public void save(Categoria categoria) {
        categoria.setUsuarioCadastro(usuarioService.getUsuarioLogado().getId());
        categoriaRepository.save(categoria);
    }

    public Categoria buscarUm(Integer id) {
        return categoriaRepository.findByIdAndUsuarioCadastroIn(id, getIdsDoUsuarioProprietario())
            .orElseThrow(CATEGORIA_NAO_ENCONTRADA::getException);
    }

    public List<Categoria> buscarTodos() {
        return categoriaRepository.findByUsuarioCadastroIn(getIdsDoUsuarioProprietario());
    }

    public void remover(Integer id) {
        Categoria categoria = categoriaRepository.findByIdAndUsuarioCadastroIn(id, getIdsDoUsuarioProprietario())
            .orElseThrow(CATEGORIA_NAO_ENCONTRADA::getException);
        if (produtoRepository.existsByCategoria(categoria)) {
            throw CATEGORIA_VINCULADA_PRODUTO.getException();
        }
        categoriaRepository.delete(categoria);
    }

    public List<Integer> getIdsDoUsuarioProprietario() {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        if (usuarioLogado.isUser()) {
            return Collections.singletonList(usuarioService.buscarUm(usuarioLogado.getId()).getUsuarioProprietario());
        } else if (usuarioLogado.isAdmin()) {
            return Collections.singletonList(usuarioLogado.getId());
        } else {
            return usuarioService.getIdsPermitidos();
        }
    }
}
