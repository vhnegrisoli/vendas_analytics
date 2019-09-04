package com.br.unifil.vendas_analytics.vendas_analytics.ExceptionMessage;

import com.br.unifil.vendas_analytics.vendas_analytics.validation.ValidacaoException;
import lombok.Getter;

public enum UsuarioExceptionMessage {

    USUARIO_NAO_ENCONTRADO(new ValidacaoException("Usuário não encontrado.")),
    USUARIO_SEM_PERMISSAO_VISUALIZAR(new ValidacaoException("Você não tem permissão para ver esse usuário.")),
    USUARIO_SEM_SESSAO(new ValidacaoException("Não há uma sessão de usuário ativa.")),
    USUARIO_SEM_PERMISSAO_REMOVER(new ValidacaoException("Você não tem permissão para remover esse usuário.")),
    USUARIO_SEM_PERMISSAO_ATUALIZAR_SENHA(new ValidacaoException("Você não tem permissão para atualizar a senha"
        + " desse usuário.")),
    USUARIO_SEM_PERMISSAO_ATUALIZAR_ULTIMO_ACESSO(new ValidacaoException("Você não tem permissão para atualizar o"
        + " último acesso desse usuário.")),
    USUARIO_EMAIL_JA_CADASTRADO(new ValidacaoException("Email já cadastrado para um usuário ativo.")),
    PERMISSAO_NAO_ENCONTRADA(new ValidacaoException("Permissão não encontrada.")),
    USUARIO_SEM_VENDEDOR(new ValidacaoException("É preciso ter um vendedor para cadastrar um novo usuário.")),
    USUARIO_COM_VENDEDOR(new ValidacaoException("Esse usuário já possui um vendedor. Um usuário não pode ser vinculado"
        + " a mais de um vendedor.")),
    USUARIO_ATIVO(new ValidacaoException("Não é possível remover um usuário ativo.")),
    USUARIO_COM_RELATORIOS(new ValidacaoException("Este usuário já possui relatórios cadastrados.")),
    USUARIO_ACESSO_INVALIDO(new ValidacaoException("Usuário ou senha inválidos, tente novamente."));

    @Getter
    private ValidacaoException exception;
    UsuarioExceptionMessage(ValidacaoException exception) {
        this.exception = exception;
    }
}
