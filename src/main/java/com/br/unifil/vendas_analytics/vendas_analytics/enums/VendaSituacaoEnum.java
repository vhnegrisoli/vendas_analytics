package com.br.unifil.vendas_analytics.vendas_analytics.enums;

public enum VendaSituacaoEnum {

    ABERTA("ABERTA"),
    FECHADA("FECHADA");

    private String situacao;

    VendaSituacaoEnum(String situacao) {
        this.situacao = situacao;
    }
}
