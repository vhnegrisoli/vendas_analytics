package com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage;

import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import lombok.Getter;

public enum VendaExceptionMessage {

    VENDA_NAO_ENCONTRADA(new ValidacaoException("Venda não encontrada.")),
    VENDA_SEM_PERMISSAO_VISUALIZAR(new ValidacaoException("Você não tem permissão para ver esta venda.")),
    VENDA_SEM_PRODUTOS(new ValidacaoException("Você deve cadastrar produtos no carrinho de compra para tratar "
        + "uma venda.")),
    VENDA_SEM_VENDEDOR(new ValidacaoException("Você deve informar um vendedor para tratar uma venda."));

    @Getter
    private ValidacaoException exception;
    VendaExceptionMessage(ValidacaoException exception) {
        this.exception = exception;
    }
}
