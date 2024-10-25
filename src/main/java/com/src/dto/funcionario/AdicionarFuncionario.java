package com.src.dto.funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdicionarFuncionario {

  @NotNull
  private String nome;

  @NotNull
  private BigDecimal salario;

  @NotNull
  private LocalDate dataAdmissao;
}
