package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.ExportarCsvDto;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ExportarCsvRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RelatorioCsvService {

    @Autowired
    private ExportarCsvRepository exportarCsvRepository;

    @Autowired
    private UsuarioService usuarioService;

    public String gerarCabecalho() {
        return "Nome do Vendedor;CPF do Vendedor;Email do Vendedor;Endereço do Vendedor;Cidade;Estado;" +
                "Região;Usuário do Vendedor;Código da Venda;Quantidade de Itens;Data da Venda;Situação" +
                " da Venda;Aprovação da Venda;Nome do Vendedor;Email do Vendedor;CPF do Vendedor;" +
                "Código do Produto;Produto;Valor do Pedido;Categoria;CNPJ do Fornecedor;" +
                "Nome do Fornecedor;Razão Social do Fornecedor";
    }

    public String gerarCsv(String dataInicial, String dataFinal) throws JsonProcessingException {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        List<ExportarCsvDto> resposta = new ArrayList<>();
        if (usuarioLogado.isSuperAdmin()) {
            if (ObjectUtils.isEmpty(dataInicial) || ObjectUtils.isEmpty(dataFinal)) {
                resposta = exportarCsvRepository.exportarCsvSuperAdminCompleto();
            } else {
                resposta = exportarCsvRepository.exportarCsvSuperAdminFiltros(dataInicial, dataFinal);
            }
        } else {
            if (ObjectUtils.isEmpty(dataInicial) || ObjectUtils.isEmpty(dataFinal)) {
                resposta = exportarCsvRepository.exportarCsvSemFiltroDeData(usuarioLogado.getId());
            } else {
                resposta = exportarCsvRepository.exportarCsvComFiltroDeData(dataInicial, dataFinal, usuarioLogado.getId());
            }
        }
        AtomicReference<String> dadosVenda = new AtomicReference<>(gerarCabecalho() + "\n");
        resposta
            .forEach(
                registro -> {
                    dadosVenda.set(dadosVenda.get() +
                            registro.getNome_vendedor() + ";" +
                            registro.getCpf_vendedor() + ";" +
                            registro.getEmail_vendedor() + ";" +
                            registro.getEndereco_vendedor() + ";" +
                            registro.getCidade() + ";" +
                            registro.getEstado() + ";" +
                            registro.getRegiao() + ";" +
                            registro.getUsuario_vendedor() + ";" +
                            registro.getCodigo_venda() + ";" +
                            registro.getQuantidade_itens() + ";" +
                            registro.getData_venda() + ";" +
                            registro.getSituacao_venda() + ";" +
                            registro.getAprovacao_venda() + ";" +
                            registro.getCliente_nome() + ";" +
                            registro.getCliente_email() + ";" +
                            registro.getCliente_cpf() + ";" +
                            registro.getCodigo_produto() + ";" +
                            registro.getProduto() + ";" +
                            registro.getValor_pedido() + ";" +
                            registro.getCategoria() + ";" +
                            registro.getCnpj_fornecedor() + ";" +
                            registro.getFornecedor_nome_fantasia() + ";" +
                            registro.getRazao_social_fornecedor() + ";\n");
                }
            );
        return dadosVenda.get();
    }

}
