package com.br.unifil.vendas_analytics.vendas_analytics.enums;

public enum CategoriaTipo {

    LIVROS("Livros"),
    SOFTWARE("Software"),
    HARDWARE("Hardware"),
    PAPELARIA("Papelaria"),
    ALIMENTOS("Alimentos"),
    FERRAMENTAS("Ferramentas"),
    CURSOS("Cursos"),
    FILMES("Filmes"),
    DISCOS("Discos"),
    ELETRONICOS("Eletrônicos");

    private String descricao;
    CategoriaTipo(String descricao) {
        this.descricao = descricao;
    }

}
