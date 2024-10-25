package com.src.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.src.exceptions.AutenticacaoInvalida;
import com.src.exceptions.ExceptionModel;
import com.src.exceptions.NaoEncontradoException;
import com.src.exceptions.UsuarioJaCadastradoException;

@ControllerAdvice
class ControllerAdvices {

  @ExceptionHandler(AutenticacaoInvalida.class)
  public ResponseEntity<ExceptionModel> autenticacaoInvalida(Exception e) {
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    ExceptionModel resposta = new ExceptionModel(status, e.getMessage());

    return new ResponseEntity<ExceptionModel>(resposta, status);
  }

  @ExceptionHandler(UsuarioJaCadastradoException.class)
  public ResponseEntity<ExceptionModel> usuarioJaCadastradoException(Exception e) {
    HttpStatus status = HttpStatus.CONFLICT;
    ExceptionModel resposta = new ExceptionModel(status, e.getMessage());

    return new ResponseEntity<ExceptionModel>(resposta, status);
  }

  @ExceptionHandler(NaoEncontradoException.class)
  public ResponseEntity<ExceptionModel> naoEncontradoException(Exception e) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ExceptionModel resposta = new ExceptionModel(status, e.getMessage());

    return new ResponseEntity<ExceptionModel>(resposta, status);
  }
}
