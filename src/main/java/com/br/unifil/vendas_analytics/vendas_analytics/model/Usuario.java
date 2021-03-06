package com.br.unifil.vendas_analytics.vendas_analytics.model;

import com.br.unifil.vendas_analytics.vendas_analytics.enums.UsuarioSituacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static org.springframework.util.ObjectUtils.isEmpty;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column
    @Basic
    private String nome;

    @NotNull
    @Column(name = "data_cadastro")
    @Basic
    private LocalDateTime dataCadastro;

    @NotNull
    @Column
    @Basic
    private String email;

    @NotNull
    @Column
    @Basic
    private String senha;

    @NotNull
    @Column
    @Basic
    @Enumerated(EnumType.STRING)
    private UsuarioSituacao situacao;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permissoes_usuario_id")
    private PermissoesUsuario permissoesUsuario;

    @Column(name = "usuario_proprietario")
    private Integer usuarioProprietario;

    @Column(name = "ultimo_acesso")
    @Basic
    private LocalDateTime ultimoAcesso;

    @JsonIgnore
    public boolean isNovoCadastro() {
        return isEmpty(this.id);
    }
}
