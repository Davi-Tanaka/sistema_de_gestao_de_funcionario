package com.src.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.src.model.entidades.UsuarioEntidade;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastrarUsuario {

  @JsonProperty("nome")
  @NotNull
  private String nome;

  @JsonProperty("email")
  @NotNull
  @Email
  private String email;

  @JsonProperty("senha")
  @NotNull
  private String senha;

  public UsuarioEntidade transformarEmEntidade() {
    return UsuarioEntidade.builder()
        .nome(this.nome)
        .email(this.email)
        .senha(this.senha)
        .build();
  }
}
