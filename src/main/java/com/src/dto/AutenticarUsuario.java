package com.src.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutenticarUsuario {
  
  @JsonProperty("email")
  @NotNull
  @Email
  private String email;

  @JsonProperty("senha")
  @NotNull
  private String senha;
  
}
