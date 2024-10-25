package com.src.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.src.dto.AutenticarUsuario;
import com.src.dto.autenticacao.TokensDeAutenticacao;
import com.src.dto.usuario.CadastrarUsuario;
import com.src.dto.usuario.UsuarioCadastradoComSucesso;
import com.src.exceptions.AutenticacaoInvalida;
import com.src.exceptions.UsuarioJaCadastradoException;
import com.src.model.entidades.UsuarioEntidade;
import com.src.model.repositorio.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthServiceTest {

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private AuthService authService;

  @Test
  public void deve_cadastrar_usuario() {
    UsuarioCadastradoComSucesso usuarioCadastrado = authService.cadastrar(new CadastrarUsuario("nome de usuario", "emaildeusuario@email.com", "senha"));

    Assertions.assertTrue(usuarioCadastrado.id() != null);
    Assertions.assertTrue(usuarioCadastrado.nome() != null);
  }

  @Test
  public void nao_deve_cadasrar_usuario_que_ja_existe() {
    
    CadastrarUsuario corpoDaRequisicao = new CadastrarUsuario(
        "nome de usuario", "abc@email.com", "123"
    );

    UsuarioEntidade usuario = UsuarioEntidade.builder()
      .nome(corpoDaRequisicao.getNome())
      .email(corpoDaRequisicao.getEmail())
      .senha(corpoDaRequisicao.getSenha())
      .build();

    usuarioRepositorio.save(usuario);
    Assertions.assertThrowsExactly(UsuarioJaCadastradoException.class, () -> authService.cadastrar(corpoDaRequisicao));
  }

  @Test
  public void nao_deve_autenticar_usuario_com_credenciais_invalidas() {
    CadastrarUsuario cadastrarUsuario = new CadastrarUsuario(
        "nome de usuario", "emaildeusuario@email.com", "senha"
    );
    
    AutenticarUsuario autenticarUsuarioDto = new AutenticarUsuario(
      cadastrarUsuario.getEmail(), 
      "SENHA INVALIDA !!"
    );
    
    authService.cadastrar(cadastrarUsuario);
    Assertions.assertThrowsExactly(AutenticacaoInvalida.class, () -> authService.authenticar(autenticarUsuarioDto));
  }

  @Test
  public void deve_autenticar_usuario_com_credenciais_validas() {
    CadastrarUsuario cadastrarUsuario = new CadastrarUsuario(
        "nome de usuario", "emaildeusuario@email.com", "senha"
    );

    AutenticarUsuario autenticarUsuarioDto = new AutenticarUsuario(
      cadastrarUsuario.getEmail(), 
      cadastrarUsuario.getSenha()
    );

    authService.cadastrar(cadastrarUsuario);
    TokensDeAutenticacao tokens = authService.authenticar(autenticarUsuarioDto);

    Assertions.assertTrue(tokens.getTokenDeAcesso() != null);
    Assertions.assertTrue(tokens.getTokenDeAtualizacao() != null);
  }
}
