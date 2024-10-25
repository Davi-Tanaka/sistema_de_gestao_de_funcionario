package com.src.exceptions;

public class UsuarioJaCadastradoException extends RuntimeException {
  public UsuarioJaCadastradoException() {
    super("Usuario ja cadastrado. Tente outro email ou nome de usuário.");
  }
}
