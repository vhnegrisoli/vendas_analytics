package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.ExportarCsvDto;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ExportarCsvRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class RelatorioCsvService {

    @Autowired
    private ExportarCsvRepository exportarCsvRepository;

    @Autowired
    private UsuarioService usuarioService;

    public String gerarCabecalho() {
        return "Nome do Vendedor;CPF do Vendedor;Email do Vendedor;Endereço do Vendedor;Cidade;Estado;"
            + "Região;Usuário do Vendedor;Código da Venda;Quantidade de Itens;Data da Venda;Situação"
            + " da Venda;Aprovação da Venda;Nome do Vendedor;Email do Vendedor;CPF do Vendedor;"
            + "Código do Produto;Produto;Valor do Pedido;Categoria;CNPJ do Fornecedor;"
            + "Nome do Fornecedor;Razão Social do Fornecedor";
    }

    public String gerarCsv(String dataInicial, String dataFinal) throws JsonProcessingException {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        List<ExportarCsvDto> resposta = new ArrayList<>();
        if (usuarioLogado.isSuperAdmin()) {
            if (isEmpty(dataInicial) || isEmpty(dataFinal)) {
                resposta = exportarCsvRepository.exportarCsvSuperAdminCompleto();
            } else {
                resposta = exportarCsvRepository.exportarCsvSuperAdminFiltros(dataInicial, dataFinal);
            }
        } else {
            if (isEmpty(dataInicial) || isEmpty(dataFinal)) {
                resposta = exportarCsvRepository.exportarCsvSemFiltroDeData(usuarioLogado.getId());
            } else {
                resposta = exportarCsvRepository.exportarCsvComFiltroDeData(dataInicial, dataFinal, usuarioLogado.getId());
            }
        }
        return criarStringCsv(resposta);
    }

    @SuppressWarnings({"checkstyle:methodlength"})
    private String criarStringCsv(List<ExportarCsvDto> resposta) {
        AtomicReference<String> dadosVenda = new AtomicReference<>(gerarCabecalho() + "\n");
        resposta
            .forEach(
                registro -> {
                    dadosVenda.set(dadosVenda.get()
                        + ";" + registro.getNomeVendedor()
                        + ";" + registro.getCpfVendedor()
                        + ";" + registro.getEmailVendedor()
                        + ";" + registro.getEnderecoVendedor()
                        + ";" + registro.getCidade()
                        + ";" + registro.getEstado()
                        + ";" + registro.getRegiao()
                        + ";" + registro.getUsuarioVendedor()
                        + ";" + registro.getCodigoVenda()
                        + ";" + registro.getQuantidadeItens()
                        + ";" + registro.getDataVenda()
                        + ";" + registro.getSituacaoVenda()
                        + ";" + registro.getAprovacaoVenda()
                        + ";" + registro.getClienteNome()
                        + ";" + registro.getClienteEmail()
                        + ";" + registro.getClienteCpf()
                        + ";" + registro.getCodigoProduto()
                        + ";" + registro.getProduto()
                        + ";" + registro.getValorPedido()
                        + ";" + registro.getCategoria()
                        + ";" + registro.getCnpjFornecedor()
                        + ";" + registro.getFornecedorNomeFantasia()
                        + ";" + registro.getRazaoSocialFornecedor() + ";\n");
                });
        return dadosVenda.get();
    }
}