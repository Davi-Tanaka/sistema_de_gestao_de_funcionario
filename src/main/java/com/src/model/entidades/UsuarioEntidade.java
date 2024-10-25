package com.src.model.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table
public class UsuarioEntidade {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public String id;
  
  @Column(unique = true)
  public String nome;
  
  @Column(unique = true)
  public String email;
  
  @Column
  public String senha;
  
  @Column
  @Builder.Default
  public LocalDateTime dataDeCriacao = LocalDateTime.now();
  
  @OneToMany(mappedBy = "usuario")
  public List<FuncionarioEntidade> funcionarios;
}
