package com.br.unifil.vendas_analytics.vendas_analytics.enums;

public enum PermissoesUsuarioEnum {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String permissao;

    PermissoesUsuarioEnum (String permissao) {
        this.permissao = permissao;
    }
}
