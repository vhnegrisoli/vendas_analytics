package com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage;

import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import lombok.Getter;

public enum ProdutoExceptionMessage {

    PRODUTO_NAO_ENCONTRADO(new ValidacaoException("Produto não encontrado.")),
    PRODUTO_VINCULADO_VENDAS(new ValidacaoException("Não é possível remover este produto pois ele já está "
        + "vinculado a vendas no sistema.")),
    FORNECEDOR_NAO_ENCONTRADO(new ValidacaoException("Fornecedor não encontrado.")),
    FORNECEDOR_VINCULADO_PRODUTO(new ValidacaoException("Não é possível remover este fornecedor pois ele já está "
        + "vinculado a um produto no sistema.")),
    CATEGORIA_NAO_ENCONTRADA(new ValidacaoException("Categoria não encontrada.")),
    CATEGORIA_VINCULADA_PRODUTO(new ValidacaoException("Não é possível remover esta categoria pois ela já está "
        + "vinculado a um produto no sistema.")),;

    @Getter
    private ValidacaoException exception;
    ProdutoExceptionMessage(ValidacaoException exception) {
        this.exception = exception;
    }
}
