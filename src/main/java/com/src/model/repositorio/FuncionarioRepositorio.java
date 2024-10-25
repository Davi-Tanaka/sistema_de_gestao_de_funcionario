package com.src.model.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.src.model.entidades.FuncionarioEntidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FuncionarioRepositorio extends JpaRepository<FuncionarioEntidade, String> {

  public Page<FuncionarioEntidade> findByUsuarioId(String usuarioId, Pageable paginacao);
  public Optional<FuncionarioEntidade> findByIdAndUsuarioId(String id, String usuarioId);

  @Query( "SELECT f FROM #{#entityName} f WHERE f.nome LIKE CONCAT(:nome, '%') AND f.usuario.id = :usuarioId ORDER BY f.id LIMIT :quantidade")
  public List<FuncionarioEntidade> pesquisarFuncionarioDeUsuarioPeloNome(
    @Param("nome") String nome,
    @Param("usuarioId") String usuarioId, 
    @Param("quantidade") Integer quantidade
  );  
}
