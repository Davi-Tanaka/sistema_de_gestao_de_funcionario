package com.src.controlador;

import com.src.dto.AutenticarUsuario;
import com.src.dto.usuario.CadastrarUsuario;
import com.src.dto.usuario.UsuarioCadastradoComSucesso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthControladorTest {

  @Autowired
  private AuthController authController;
  
  @Test
  public void rota_auth_cadastrar__deve_cadastrar() {
    CadastrarUsuario usuario = new CadastrarUsuario(
        "nome-de-usuairo", "emaildeusuario@usuario.com", "1234567890"
    );
    
    UsuarioCadastradoComSucesso controladorResposta = authController.cadastrar(usuario);
    
    Assertions.assertEquals(controladorResposta.nome(), usuario.getNome());
    Assertions.assertNotNull(controladorResposta.id());
  }

  @Test
  public void rota_auth_login__deve_autenticar() {
    CadastrarUsuario usuario = new CadastrarUsuario(
        "nome-de-usuairo",
        "emaildeusuario@usuario.com",
        "1234567890"
    );

    authController.cadastrar(usuario);

    ResponseEntity<String> res = authController.login(
        new AutenticarUsuario(usuario.getEmail(), usuario.getSenha()),
        new MockHttpServletResponse()
    );
    
    Assertions.assertTrue(res.getBody().toLowerCase().equals("ok"));
    Assertions.assertEquals(res.getStatusCode().value(), 200);
  }
}
