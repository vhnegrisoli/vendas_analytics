package com.br.unifil.vendas_analytics.vendas_analytics.enums;

public enum PermissoesUsuarioEnum {

    SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    String permissao;

    PermissoesUsuarioEnum (String permissao) {
        this.permissao = permissao;
    }
}
