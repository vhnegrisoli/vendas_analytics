package com.br.unifil.vendas_analytics.vendas_analytics.enums;

public enum UsuarioSituacao {

    ATIVO("ATIVO"),
    INATIVO("INATIVO");

    private String situacao;
    UsuarioSituacao(String situacao) {
        this.situacao = situacao;
    }
}
