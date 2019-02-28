package com.br.unifil.vendas_analytics.vendas_analytics.enums;

public enum VendaAprovacaoEnum {

    APROVADA("APROVADA"),
    AGUARDANDO_APROVACAO("AGUARDANDO APROVAÇÃO"),
    REJEITADA("REJEITADA");

    private String aprovacao;

    VendaAprovacaoEnum(String aprovacao) {
        this.aprovacao = aprovacao;
    }
}
