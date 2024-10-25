package com.src.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.src.dto.AutenticarUsuario;
import com.src.dto.autenticacao.CorpoDoTokenDeAcesso;
import com.src.dto.autenticacao.CorpoDoTokenDeAtualizacao;
import com.src.dto.autenticacao.TokensDeAutenticacao;
import com.src.dto.usuario.CadastrarUsuario;
import com.src.dto.usuario.UsuarioCadastradoComSucesso;
import com.src.exceptions.AutenticacaoInvalida;
import com.src.exceptions.UsuarioJaCadastradoException;
import com.src.model.entidades.UsuarioEntidade;
import com.src.model.repositorio.UsuarioRepositorio;

@Service
public class AuthService {
  
  @Autowired
  private UsuarioRepositorio usuarioRepositorio;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Autowired
  private JwtService jwtService;
  
  public TokensDeAutenticacao authenticar(AutenticarUsuario data) {
    Optional<UsuarioEntidade> usuarioOptional = usuarioRepositorio.findByEmail(data.getEmail());

    if(usuarioOptional.isEmpty()) {
      throw new AutenticacaoInvalida();
    }

    UsuarioEntidade usuario = usuarioOptional.get();
    boolean crendenciaisSaoValidas = passwordEncoder.matches(data.getSenha(), usuario.getSenha());


    if(!crendenciaisSaoValidas) {
      throw new AutenticacaoInvalida();
    }

    try {
      String tokenDeAcesso = jwtService.gerarTokenDeAcesso(
          new CorpoDoTokenDeAcesso(usuario.getId(), usuario.getNome())
      );

      String tokenDeAtualizacao = jwtService.gerarTokenDeAtualizacao(
          new CorpoDoTokenDeAtualizacao(usuario.getId())
      );

      return new TokensDeAutenticacao(tokenDeAcesso, tokenDeAtualizacao);
    } catch(Exception error) {
      if(error.getClass().equals(JsonProcessingException.class)) {
        error.printStackTrace();
      }
      
      throw new AutenticacaoInvalida();
    }
  }
  
  public UsuarioCadastradoComSucesso cadastrar(CadastrarUsuario data) {    
    if(usuarioRepositorio.findByEmail(data.getEmail()).isPresent()) {
      throw new UsuarioJaCadastradoException();
    }

    UsuarioEntidade usuario = data.transformarEmEntidade();
    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

    UsuarioEntidade usuarioSalvo = usuarioRepositorio.save(usuario);
    return new UsuarioCadastradoComSucesso(usuarioSalvo.getId(), usuarioSalvo.getNome());
  }
}
