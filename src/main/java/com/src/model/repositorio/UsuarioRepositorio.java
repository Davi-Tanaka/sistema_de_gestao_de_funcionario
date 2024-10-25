package com.src.model.repositorio;

import com.src.model.entidades.UsuarioEntidade;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidade, String>{
  public Optional<UsuarioEntidade> findByEmail(String email);
  public Optional<UsuarioEntidade> findByNome(String nome);

}