package com.src.dto.autenticacao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@Jacksonized
@Builder

@JsonIgnoreProperties(ignoreUnknown = true)
public class CorpoDoTokenDeAtualizacao {
  @JsonProperty("id")
  private String id;
}
