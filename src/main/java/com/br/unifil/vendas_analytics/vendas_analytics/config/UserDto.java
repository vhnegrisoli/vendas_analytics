package com.br.unifil.vendas_analytics.vendas_analytics.config;

import com.br.unifil.vendas_analytics.vendas_analytics.model.PermissoesUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String nome;
    private String email;
    private PermissoesUsuario permissao;

}
