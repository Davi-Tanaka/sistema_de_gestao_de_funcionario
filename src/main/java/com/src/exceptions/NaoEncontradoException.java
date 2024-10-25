package com.src.exceptions;

public class NaoEncontradoException extends RuntimeException {
  
  public NaoEncontradoException() {
    super("Não foi possivel encontrar item requisitado.");
  }

  public NaoEncontradoException(String msg) {
    super(msg);
  }
}
