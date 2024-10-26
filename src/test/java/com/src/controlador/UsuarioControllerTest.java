package com.src.controlador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.src.details.UserDetailsImp;
import com.src.dto.usuario.CadastrarUsuario;
import com.src.dto.usuario.UsuarioCadastradoComSucesso;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UsuarioControllerTest {

  @Autowired
  private AuthController authController;
  
  @Autowired
  private UsuarioController usuarioController;
   
  @Test
  public void deve_deslogar_de_conta() throws JsonProcessingException {
    UsuarioCadastradoComSucesso usuarioCadastrado = authController.cadastrar(new CadastrarUsuario(
        "nome", 
        "email@mock.com", 
        "123123123"
    ));
    
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
    
    ModelAndView rota_deslogar_resposta = usuarioController.deslogar(
        new UserDetailsImp(usuarioCadastrado.id(), usuarioCadastrado.nome()), 
        httpServletResponse
    );
    
    Cookie tokenDeAcessoCookie = httpServletResponse.getCookie("TOKEN-DE-ACESSO");
    Cookie tokenDeAtualizacaoCookie = httpServletResponse.getCookie("TOKEN-DE-ATUALIZACAO");
    
    Assertions.assertEquals(rota_deslogar_resposta.getViewName(), "redirect:/auth/login");    
    Assertions.assertEquals(tokenDeAcessoCookie.getMaxAge(), 0);
    Assertions.assertEquals(tokenDeAcessoCookie.getMaxAge(), 0);
    Assertions.assertNull(tokenDeAcessoCookie.getValue());
    Assertions.assertNull(tokenDeAtualizacaoCookie.getValue());
  }
}