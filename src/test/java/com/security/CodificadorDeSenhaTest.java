package com.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author davi
 */
public class CodificadorDeSenhaTest {

  private CodificadorDeSenha codificador = new CodificadorDeSenha(5);

  @Test
  @DisplayName("Deve codificar senha")
  public void deveCodificarSenha() {
    String senha = "123";
    String senhaCodificada = codificador.codificar(senha);

    Assertions.assertTrue(senhaCodificada.length() > 1);
  }

  @Test
  @DisplayName("Deve retornar true se senhas correspondem")

  public void deveRetornarTrueSeSenhasCorrespondem() {
    String senha = "123";
    String senhaCodificada = codificador.codificar(senha);
    boolean senhasCorrespondem = codificador.corresponde(senha, senhaCodificada);


    Assertions.assertTrue(senhasCorrespondem);

  }

  @Test
  @DisplayName("Deve retornar false se senhas nao correspondem")

  public void deveRetornarFalseSeSenhasNaoCorrespondem() {
    String senha = "123";
    String senha_que_nao_corresponde = "321";

    String senhaCodificada = codificador.codificar(senha);
    boolean senhasCorrespondem = codificador.corresponde(senha_que_nao_corresponde, senhaCodificada);

    Assertions.assertFalse(senhasCorrespondem);
  }
}
