package com.src.dto.autenticacao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokensDeAutenticacao {
  private String tokenDeAcesso;
  private String tokenDeAtualizacao;
}
