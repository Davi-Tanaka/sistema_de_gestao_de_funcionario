package com.src.services;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.src.config.JwtPropriedades;
import com.src.dto.autenticacao.CorpoDoTokenDeAcesso;
import com.src.dto.autenticacao.CorpoDoTokenDeAtualizacao;

public class JwtServiceTest {

  private static JwtService jwtService;

  @BeforeAll
  public static void inicio() {
    JwtPropriedades prop = new JwtPropriedades("chave secreta de acesso", "chave secreta de atualizacao", 900, 3600);

    jwtService = JwtService.builder()
      .propriedades(prop)
      .objectMapper(new ObjectMapper())
      .build();

    jwtService.montrarAoIniciar();
  }

  @Test
  public void deve_gerar_token_de_acesso() throws JsonProcessingException {
    String token = jwtService.gerarTokenDeAcesso(new CorpoDoTokenDeAcesso(UUID.randomUUID().toString(), "nome de usuario"));
    Assertions.assertTrue(token.split("\\.").length == 3);
  }

  @Test
  public void deve_gerar_token_de_atualizacao() throws JsonProcessingException {
    String token = jwtService.gerarTokenDeAtualizacao(new CorpoDoTokenDeAtualizacao(UUID.randomUUID().toString()));
    Assertions.assertTrue(token.split("\\.").length == 3);
  }
}
