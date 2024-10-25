package com.src.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.dto.funcionario.AdicionarFuncionario;
import com.src.dto.funcionario.FuncionarioAdicionado;
import com.src.exceptions.NaoEncontradoException;
import com.src.model.entidades.FuncionarioEntidade;
import com.src.model.entidades.UsuarioEntidade;
import com.src.model.repositorio.FuncionarioRepositorio;
import com.src.model.repositorio.UsuarioRepositorio;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
public class FuncionarioService {

  @Autowired
  private FuncionarioRepositorio funcionarioRepositorio;

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  public FuncionarioAdicionado adicionar( String usuarioId, AdicionarFuncionario dto ) {

    UsuarioEntidade usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(() -> new NaoEncontradoException());

    FuncionarioEntidade ent = FuncionarioEntidade.builder()
      .nome(dto.getNome())
      .dataAdmissao(dto.getDataAdmissao())
      .salario(dto.getSalario())
      .usuario(usuario)
      .build();

    FuncionarioEntidade funcionarioSalvo = funcionarioRepositorio.save(ent);

    return new FuncionarioAdicionado(
      funcionarioSalvo.getId(), 
      funcionarioSalvo.getNome(), 
      funcionarioSalvo.getDataAdmissao()
    );
  }

  public void remover(String usuarioId, String funcionarioId) {
    usuarioRepositorio.findById(usuarioId)
      .orElseThrow(() -> new NaoEncontradoException());
        
    FuncionarioEntidade entidadeParaSerDeletado = funcionarioRepositorio.findByIdAndUsuarioId(
      funcionarioId, 
      usuarioId
    ).orElseThrow(() -> new NaoEncontradoException());

    funcionarioRepositorio.delete(entidadeParaSerDeletado);
  } 
  
  public List<FuncionarioEntidade> pesquisarPeloNome(String usuarioId, String nome) {
    return this.funcionarioRepositorio.pesquisarFuncionarioDeUsuarioPeloNome(
        nome, 
        usuarioId, 
        10
    );
  }
  
  public List<FuncionarioEntidade> retornarFuncionariosDeUsuario(
      String usuarioId, 
      int pagina,
      int quant
  ) {
    return this.funcionarioRepositorio.findByUsuarioId(
        usuarioId, 
        PageRequest.of(pagina - 1, quant, Sort.by(Sort.Direction.ASC, "nome"))
    ).getContent();
  }
  
  public FuncionarioEntidade buscarPeloIdOrLanceError(String id, String usuarioId) {
    return this.funcionarioRepositorio.findByIdAndUsuarioId(id, usuarioId).orElseThrow(
        () -> new NaoEncontradoException()
    );
  }
}