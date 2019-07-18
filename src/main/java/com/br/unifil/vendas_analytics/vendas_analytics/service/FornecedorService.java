package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.model.Fornecedor;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.FornecedorRepository;
import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private UsuarioService usuarioService;

    private final static ValidacaoException FORNECEDOR_NOT_FOUND_EXCEPTION =
        new ValidacaoException("Fornecedor não encontrado");

    public void save(Fornecedor fornecedor) {
        fornecedor.setUsuarioCadastro(usuarioService.getUsuarioLogado().getId());
        fornecedorRepository.save(fornecedor);
    }

    public Fornecedor buscarUm(Integer id) {
        return fornecedorRepository.findByIdAndUsuarioCadastroIn(id, getIdsDoUsuarioProprietario())
            .orElseThrow(() -> FORNECEDOR_NOT_FOUND_EXCEPTION);
    }

    public List<Fornecedor> buscarTodos() {
        return fornecedorRepository.findByUsuarioCadastroIn(getIdsDoUsuarioProprietario());
    }

    public void remover(Integer id) {
        Fornecedor fornecedor = fornecedorRepository.findByIdAndUsuarioCadastroIn(id, getIdsDoUsuarioProprietario())
            .orElseThrow(() -> FORNECEDOR_NOT_FOUND_EXCEPTION);
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
