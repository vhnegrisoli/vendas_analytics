package com.br.unifil.vendas_analytics.vendas_analytics.service;

import com.br.unifil.vendas_analytics.vendas_analytics.config.UsuarioAutenticadoDto;
import com.br.unifil.vendas_analytics.vendas_analytics.dto.ExportarCsvDto;
import com.br.unifil.vendas_analytics.vendas_analytics.repository.ExportarCsvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        return "Nome do Vendedor;CPF do Vendedor;Email do Vendedor;Endereco do Vendedor;Cidade;Estado;"
            + "Regiao;Usuario do Vendedor;Codigo da Venda;Quantidade de Itens;Data da Venda;Situacao"
            + " da Venda;Aprovacao da Venda;Nome do Vendedor;Email do Vendedor;CPF do Vendedor;"
            + "Codigo do Produto;Produto;Valor do Pedido;Categoria;CNPJ do Fornecedor;"
            + "Nome do Fornecedor;Razao Social do Fornecedor";
    }

    public String gerarCsv(String dataInicial, String dataFinal) {
        UsuarioAutenticadoDto usuarioLogado = usuarioService.getUsuarioLogado();
        List<ExportarCsvDto> resposta;
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
                        + validarCampoStringNulo(registro.getNomeVendedor())
                        + ";" + validarCampoStringNulo(registro.getCpfVendedor())
                        + ";" + validarCampoStringNulo(registro.getEmailVendedor())
                        + ";" + validarCampoStringNulo(registro.getEnderecoVendedor())
                        + ";" + validarCampoStringNulo(registro.getCidade())
                        + ";" + validarCampoStringNulo(registro.getEstado())
                        + ";" + validarCampoStringNulo(registro.getRegiao())
                        + ";" + validarCampoStringNulo(registro.getUsuarioVendedor())
                        + ";" + validarCampoIntegerNulo(registro.getCodigoVenda())
                        + ";" + validarCampoIntegerNulo(registro.getQuantidadeItens())
                        + ";" + validarData(validarCampoStringNulo(registro.getDataVenda()))
                        + ";" + validarCampoStringNulo(registro.getSituacaoVenda())
                        + ";" + validarCampoStringNulo(registro.getAprovacaoVenda())
                        + ";" + validarCampoStringNulo(registro.getClienteNome())
                        + ";" + validarCampoStringNulo(registro.getClienteEmail())
                        + ";" + validarCampoStringNulo(registro.getClienteCpf())
                        + ";" + validarCampoIntegerNulo(registro.getCodigoProduto())
                        + ";" + validarCampoStringNulo(registro.getProduto())
                        + ";" + validarCampoDoubleNulo(registro.getValorPedido())
                        + ";" + validarCampoStringNulo(registro.getCategoria())
                        + ";" + validarCampoStringNulo(registro.getCnpjFornecedor())
                        + ";" + validarCampoStringNulo(registro.getFornecedorNomeFantasia())
                        + ";" + validarCampoStringNulo(registro.getRazaoSocialFornecedor()) + ";\n");
                });
        return dadosVenda.get();
    }

    private String validarCampoStringNulo(String campo) {
        if (isEmpty(campo)) {
            return "";
        }
        return campo;
    }

    private String validarData(String data) {
        return data.equals("") ? ""
            : LocalDateTime.parse(data.replace(" ", "T")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    private String validarCampoIntegerNulo(Integer campo) {
        if (isEmpty(campo)) {
            return "";
        }
        return campo.toString();
    }

    private String validarCampoDoubleNulo(Double campo) {
        if (isEmpty(campo)) {
            return "";
        }
        return campo.toString();
    }
}