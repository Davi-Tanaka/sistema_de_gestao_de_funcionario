package com.src.entidades;

import java.time.LocalDateTime;

/**
 * FuncionarioEntidade
 */
public class FuncionarioEntidade {

  private Integer id;
  private String nome;
  private String salario;
  private LocalDateTime dataAdmissao;

  public LocalDateTime dataDeCriacao = LocalDateTime.now();

  FuncionarioEntidade(Integer id, String nome, String salario, LocalDateTime dataAdmissao) {
    this.id = id;
    this.nome = nome;
    this.salario = salario;
    this.dataAdmissao = dataAdmissao;
  }

  FuncionarioEntidade(String nome, String salario, LocalDateTime dataAdmissao) {
    this.nome = nome;
    this.salario = salario;
    this.dataAdmissao = dataAdmissao;
  }

  public Integer getId() {
      return id;
  }

  public String getNome() {
      return nome;
  }

  public String getSalario() {
      return salario;
  }

  public LocalDateTime getDataAdmissao() {
      return dataAdmissao;
  }
}
