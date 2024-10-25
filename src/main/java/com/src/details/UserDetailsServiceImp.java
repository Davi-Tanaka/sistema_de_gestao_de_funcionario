package com.src.details;

import com.src.model.entidades.UsuarioEntidade;
import com.src.model.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
  
  @Autowired
  private UsuarioRepositorio usuarioRepositorio;
  
  @Override
  public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
    UsuarioEntidade usuario = usuarioRepositorio.findByNome(nome)
        .orElseThrow(() -> new UsernameNotFoundException("Não foi possivel encontrar usuario"));
    
    return new UserDetailsImp(usuario.getId(), usuario.getNome());
  }
  
  public UserDetails carregarUsuarioPeloId(String id) throws UsernameNotFoundException {
    UsuarioEntidade usuario = usuarioRepositorio.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("Não foi possivel encontrar usuario"));

    return new UserDetailsImp(usuario.getId(), usuario.getNome());
  }
}
