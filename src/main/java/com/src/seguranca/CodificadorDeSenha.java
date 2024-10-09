package com.security;

import com.password4j.BcryptFunction;

/**
 *
 * @author davi
 */
public class CodificadorDeSenha implements ICodificadorDeSenha {
  private BcryptFunction bcrypt;
  
  public CodificadorDeSenha(int rodadas) {
    this.bcrypt = BcryptFunction.getInstance(rodadas);
  }

  @Override
  public String codificar(String senha) {
    return this.bcrypt.hash(senha).getResult();
  }

  @Override
  public boolean corresponde(String senhaNaoCodificada, String senhaCodificada) {
    return this.bcrypt.check(senhaNaoCodificada, senhaCodificada);
  }
}
