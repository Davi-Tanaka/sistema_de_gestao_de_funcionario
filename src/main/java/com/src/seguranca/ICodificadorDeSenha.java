package com.security;

/**
 *
 * @author davi
 */
public interface ICodificadorDeSenha {
  /**
    * Codifica a senha do usuário.
    * @return String
    */
  public String codificar(String senha);
  /**
   * Verifica se a senha não codificada corresponde com a senha codificada.
   * @return boolean
   */
  public boolean corresponde(String senhaNaoCodificada, String senhaCodificada);
}
