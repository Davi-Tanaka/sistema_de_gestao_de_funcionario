package com.src.details;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImp implements UserDetails {

  private String id;
  private String nome;

  public UserDetailsImp(String id, String nome) {
    this.id = id;
    this.nome = nome;
  }
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return this.nome;
  }
  
  public String getId() {
    return this.id;
  }
}
