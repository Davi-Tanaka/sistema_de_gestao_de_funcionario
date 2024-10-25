package com.src.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtPropriedades {
  private String chaveSecretaDoTokenDeAcesso;
  private String chaveSecretaDoTokenDeAtualizacao;
  
  private Integer duracaoDoTokenDeAcesso;
  private Integer duracaoDoTokenDeAtualizacao;
}
