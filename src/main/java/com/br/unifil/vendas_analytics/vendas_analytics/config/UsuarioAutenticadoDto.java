package com.br.unifil.vendas_analytics.vendas_analytics.config;

import com.br.unifil.vendas_analytics.vendas_analytics.model.PermissoesUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.br.unifil.vendas_analytics.vendas_analytics.enums.PermissoesUsuarioEnum.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAutenticadoDto {

    private Integer id;
    private String nome;
    private String email;
    private PermissoesUsuario permissao;

    public boolean isAdmin() {
        return permissao.getPermissao().equals(ADMIN);
    }

    public boolean isSuperAdmin() {
        return permissao.getPermissao().equals(SUPER_ADMIN);
    }

    public boolean isUser() {
        return permissao.getPermissao().equals(USER);
    }
}
