package com.src.entidades;

import java.time.LocalDateTime;
import java.util.List;

/**
 * UsuarioEntidade
 */
public class UsuarioEntidade {

  public Integer id;
  public String nome;
  public String email;
  public String senha;

  public LocalDateTime dataDeCriacao = LocalDateTime.now();

  public List<FuncionarioEntidade> funcionarios;

  UsuarioEntidade(Integer id, String nome, String email, String senha) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.senha = senha;
  }

  UsuarioEntidade(String nome, String email, String senha) {
    this.nome = nome;
    this.email = email;
    this.senha = senha;
  }

  public Integer getId() {
      return id;
  }

  public String getEmail() {
      return email;
  }

  public String getSenha() {
      return senha;
  }

  public List<FuncionarioEntidade> getFuncionarios() {
      return funcionarios;
  }

  public String getNome() {
      return nome;
  }
}
