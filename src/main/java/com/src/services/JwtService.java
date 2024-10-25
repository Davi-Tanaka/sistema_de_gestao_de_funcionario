package com.src.services;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.src.config.JwtPropriedades;
import com.src.dto.autenticacao.CorpoDoTokenDeAcesso;
import com.src.dto.autenticacao.CorpoDoTokenDeAtualizacao;

import io.micrometer.common.lang.NonNull;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtService {

  @NonNull
  @Autowired
  private JwtPropriedades propriedades;

  @NonNull
  @Autowired
  private ObjectMapper objectMapper;

  private Algorithm tokenDeAcessoAlgoritimo;
  private Algorithm tokenDeAtualizacaoAlgoritimo;
  private JWTCreator.Builder tokenAcessoBuilder;
  private JWTCreator.Builder tokenAtualizacaoBuilder;

  private JWTVerifier verificarDeTokenDeAcesso;
  private JWTVerifier verificadorDeTokenDeAtualizacao;

  @PostConstruct
  public void montrarAoIniciar() {
    tokenDeAcessoAlgoritimo = Algorithm.HMAC512(propriedades.getChaveSecretaDoTokenDeAcesso());
    tokenDeAtualizacaoAlgoritimo = Algorithm.HMAC512(propriedades.getChaveSecretaDoTokenDeAtualizacao());

    verificadorDeTokenDeAtualizacao = JWT.require(tokenDeAcessoAlgoritimo).build();
    verificarDeTokenDeAcesso = JWT.require(tokenDeAcessoAlgoritimo).build();

    tokenAcessoBuilder = JWT
        .create()
        .withSubject("TOKEN-DE-ACESSO")
        .withExpiresAt(
            Instant.now().plusSeconds(propriedades.getDuracaoDoTokenDeAcesso())
        );

    tokenAtualizacaoBuilder = JWT
        .create()
        .withSubject("TOKEN-DE-ATUALIZACAO")
        .withExpiresAt(
            Instant.now().plusSeconds(propriedades.getDuracaoDoTokenDeAtualizacao())
        );
  }

  public String gerarTokenDeAcesso(CorpoDoTokenDeAcesso corpo)
      throws JsonProcessingException {

    return tokenAcessoBuilder
        .withIssuedAt(Instant.now())
        .withJWTId(UUID.randomUUID().toString())
        .withPayload(objectMapper.writeValueAsString(corpo))
        .sign(tokenDeAcessoAlgoritimo);
  }

  public String gerarTokenDeAtualizacao(CorpoDoTokenDeAtualizacao corpo)
      throws JsonProcessingException {

    return tokenAtualizacaoBuilder
        .withIssuedAt(Instant.now())
        .withJWTId(UUID.randomUUID().toString())
        .withPayload(objectMapper.writeValueAsString(corpo))
        .sign(tokenDeAtualizacaoAlgoritimo);
  }

  public boolean tokenDeAcessoEhValida(String token) {
    try {
      verificarDeTokenDeAcesso.verify(token);
      return true;
    } catch (Exception error) {
      return false;
    }
  }

  public boolean tokenDeAtualizacaoEhValida(String token) {
    try {
      verificadorDeTokenDeAtualizacao.verify(token);
      return true;
    } catch (Exception error) {
      return false;
    }
  }
  
  public CorpoDoTokenDeAcesso lerTokenDeAcesso(String token) throws JsonProcessingException {
    String corpoEmBase64 = verificarDeTokenDeAcesso.verify(token).getPayload();
    String json = new String(Base64.getDecoder().decode(corpoEmBase64));
    return objectMapper.readValue(json, CorpoDoTokenDeAcesso.class);  
  }
  
    public CorpoDoTokenDeAtualizacao lerTokenDeAtualizacao(String token) throws JsonProcessingException {
    String corpoEmBase64 = verificarDeTokenDeAcesso.verify(token).getPayload();
    String json = new String(Base64.getDecoder().decode(corpoEmBase64));
    return objectMapper.readValue(json, CorpoDoTokenDeAtualizacao.class);  
  }
}
