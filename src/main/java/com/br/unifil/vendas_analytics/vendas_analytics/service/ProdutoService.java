package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Produto;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ProdutoRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ProdutoVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage.ProdutoExceptionMessage.PRODUTO_NAO_ENCONTRADO;
import static com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage.ProdutoExceptionMessage.PRODUTO_VINCULADO_VENDAS;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoVendaRepository produtoVendaRepository;

    public void save(Produto produto) {
        produto.setUsuarioCadastro(usuarioService.getUsuarioLogado().getId());
        produtoRepository.save(produto);
    }

    public Produto buscarUm(Integer id) {
        return produtoRepository.findByIdAndUsuarioCadastroIn(id, getIdsDoUsuarioProprietario())
            .orElseThrow(PRODUTO_NAO_ENCONTRADO::getException);
    }

    public List<Produto> buscarTodos() {
        return produtoRepository.findByUsuarioCadastroIn(getIdsDoUsuarioProprietario());
    }

    public void remover(Integer id) {
        if (produtoVendaRepository.existsByProdutoId(id)) {
            throw PRODUTO_VINCULADO_VENDAS.getException();
        }
        Produto produto = produtoRepository.findByIdAndUsuarioCadastroIn(id, getIdsDoUsuarioProprietario())
            .orElseThrow(PRODUTO_NAO_ENCONTRADO::getException);
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