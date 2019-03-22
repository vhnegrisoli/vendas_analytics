package com.br.unifil.vendas_analytics.vendas_analytics.validation;

import lombok.Data;

@Data
public class ValidacaoExceptionDetails {

    private String title;
    private int status;
    private String details;
    private long timestamp;

}
