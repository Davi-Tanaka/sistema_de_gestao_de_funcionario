package com.src.controlador;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.src.details.UserDetailsImp;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

  @PostMapping("/sair")
  public ModelAndView deslogar(
    @AuthenticationPrincipal UserDetailsImp usuario,
    HttpServletResponse response
  ) {
    Cookie tokenDeAcessoCokie = new Cookie("TOKEN-DE-ACESSO", null);
    tokenDeAcessoCokie.setMaxAge(0);
    tokenDeAcessoCokie.setPath("/");

    Cookie tokenDeAtualizacaoCokie = new Cookie("TOKEN-DE-ATUALIZACAO", null);
    tokenDeAtualizacaoCokie.setMaxAge(0);
    tokenDeAtualizacaoCokie.setPath("/");

    if(tokenDeAcessoCokie != null) {
      tokenDeAcessoCokie.setMaxAge(0);
      tokenDeAcessoCokie.setValue(null);
      tokenDeAcessoCokie.setHttpOnly(true);
      response.addCookie(tokenDeAcessoCokie);
    }

    if(tokenDeAtualizacaoCokie != null) {
      tokenDeAtualizacaoCokie.setMaxAge(0);
      tokenDeAtualizacaoCokie.setValue(null);
      tokenDeAtualizacaoCokie.setHttpOnly(true);
      response.addCookie(tokenDeAtualizacaoCokie);
    }
    
    response.setStatus(301);

    return new ModelAndView("redirect:/auth/login");
  }
}
