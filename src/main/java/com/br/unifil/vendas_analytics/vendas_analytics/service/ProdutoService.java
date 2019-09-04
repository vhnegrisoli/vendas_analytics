package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Produto;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ProdutoRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioService usuarioService;

    private static final ValidacaoException PRODUTO_NOT_FOUND_EXCEPTION =
        new ValidacaoException("Produto não encontrado");

    public void save(Produto produto) {
        produto.setUsuarioCadastro(usuarioService.getUsuarioLogado().getId());
        produtoRepository.save(produto);
    }

    public Produto buscarUm(Integer id) {
        return produtoRepository.findByIdAndUsuarioCadastroIn(id, getIdsDoUsuarioProprietario())
            .orElseThrow(() -> PRODUTO_NOT_FOUND_EXCEPTION);
    }

    public List<Produto> buscarTodos() {
        return produtoRepository.findByUsuarioCadastroIn(getIdsDoUsuarioProprietario());
    }

    public void remover(Integer id) {
        Produto produto = produtoRepository.findByIdAndUsuarioCadastroIn(id, getIdsDoUsuarioProprietario())
            .orElseThrow(() -> PRODUTO_NOT_FOUND_EXCEPTION);
        produtoRepository.delete(produto);
    }

    public Integer getTotalProdutos() {
        return produtoRepository.countByUsuarioCadastro(usuarioService.getUsuarioLogado().getId());
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