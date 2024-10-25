package com.src.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionModel {
  private HttpStatus status;
  private String mensagem;
}
