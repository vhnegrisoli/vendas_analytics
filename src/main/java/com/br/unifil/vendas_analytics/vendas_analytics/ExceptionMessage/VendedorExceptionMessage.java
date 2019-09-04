package com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage;

import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import lombok.Getter;

public enum VendedorExceptionMessage {

    VENDEDOR_NAO_ENCONTRADO(new ValidacaoException("Vendedor não encontrado.")),
    VENDEDOR_SEM_PERMISSAO_VISUALIZAR(new ValidacaoException("Você não tem permissão para ver esse vendedor.")),
    VENDEDOR_ERRO_SALVAR(new ValidacaoException("Não foi possível salvar o vendedor.")),
    VENDEDOR_SEM_PERMISSAO_REMOVER(new ValidacaoException("Você não tem permissão para remover esse vendedor.")),
    VENDEDOR_EMAIL_INVALIDO(new ValidacaoException("Vendedor sem usuário ou com email inválido.")),
    VENDEDOR_EMAIL_JA_CADASTRADO(new ValidacaoException("Email já cadastrado.")),
    VENDEDOR_CPF_JA_CADASTRADO(new ValidacaoException("CPF já cadastrado.")),
    VENDEDOR_VENDAS_CADASTRADAS(new ValidacaoException("O vendedor não pode ser removido pois já possui vendas"
        + " cadastradas.")),
    VENDEDOR_SEM_USUARIO_ATIVO(new ValidacaoException("Não existe um usuário ativo para este vendedor.")),
    VENDEDOR_SEM_DATA_NASCIMENTO(new ValidacaoException("Por favor, preencha a data de nascimento para prosseguir."));

    @Getter
    private ValidacaoException exception;
    VendedorExceptionMessage(ValidacaoException exception) {
        this.exception = exception;
    }
}
