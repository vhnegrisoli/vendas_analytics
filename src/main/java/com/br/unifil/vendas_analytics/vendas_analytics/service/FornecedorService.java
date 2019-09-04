package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Fornecedor;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.FornecedorRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage.ProdutoExceptionMessage.FORNECEDOR_NAO_ENCONTRADO;
import static com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage.ProdutoExceptionMessage.FORNECEDOR_VINCULADO_PRODUTO;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoRepository produtoRepository;

    public void save(Fornecedor fornecedor) {
        fornecedor.setUsuarioCadastro(usuarioService.getUsuarioLogado().getId());
        fornecedorRepository.save(fornecedor);
    }

    public Fornecedor buscarUm(Integer id) {
        return fornecedorRepository.findByIdAndUsuarioCadastroIn(id, getIdsDoUsuarioProprietario())
            .orElseThrow(FORNECEDOR_NAO_ENCONTRADO::getException);
    }

    public List<Fornecedor> buscarTodos() {
        return fornecedorRepository.findByUsuarioCadastroIn(getIdsDoUsuarioProprietario());
    }

    public void remover(Integer id) {
        Fornecedor fornecedor = fornecedorRepository.findByIdAndUsuarioCadastroIn(id, getIdsDoUsuarioProprietario())
            .orElseThrow(FORNECEDOR_NAO_ENCONTRADO::getException);
        if (produtoRepository.existsByFornecedor(fornecedor)) {
            throw FORNECEDOR_VINCULADO_PRODUTO.getException();
        }
        fornecedorRepository.delete(fornecedor);
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
