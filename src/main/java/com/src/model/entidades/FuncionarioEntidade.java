package com.src.model.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table
public class FuncionarioEntidade {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  
  @Column
  private String nome;

  @Column
  private BigDecimal salario;
  
  @Column
  private LocalDate dataAdmissao;
  
  @Column
  @Builder.Default
  public LocalDateTime dataDeCriacao = LocalDateTime.now();
  
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private UsuarioEntidade usuario;
}
