package com.src.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.src.config.JwtPropriedades;
import com.src.details.UserDetailsImp;
import com.src.dto.AutenticarUsuario;
import com.src.dto.autenticacao.TokensDeAutenticacao;
import com.src.dto.usuario.CadastrarUsuario;
import com.src.dto.usuario.UsuarioCadastradoComSucesso;
import com.src.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/auth")
@RestController
public class AuthController {  
  
  @Autowired
  private JwtPropriedades jwtPropriedades;
  
  @Autowired
  private AuthService authServices;
  
  @GetMapping("/cadastrar")
  public ModelAndView mostrarTelaDeCadastro(@AuthenticationPrincipal UserDetailsImp usuario) {
    if(usuario != null) {
      return new ModelAndView("redirect:/");
    } else {
      return new ModelAndView("auth/cadastrar/index");
    }
  }

  @GetMapping("/login")
  public ModelAndView mostrarTelaDeLogin(@AuthenticationPrincipal UserDetailsImp usuario) {
    if(usuario != null) {
      return new ModelAndView("redirect:/");
    } else {
      return new ModelAndView("auth/login/index");
    }
  }
  
  @PostMapping("/cadastrar")
  @ResponseStatus(HttpStatus.CREATED)
  public UsuarioCadastradoComSucesso cadastrar(@RequestBody CadastrarUsuario corpoDaRequisicao) {
    return authServices.cadastrar(corpoDaRequisicao);
  }
 
  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<String> login(
      @RequestBody AutenticarUsuario corpoDaRequisicao,
      HttpServletResponse response
  ) {
    
    TokensDeAutenticacao tokens = authServices.authenticar(corpoDaRequisicao);

    Cookie cookieComTokenDeAcesso = new Cookie("TOKEN-DE-ACESSO", tokens.getTokenDeAcesso());
    Cookie cookieComTokenDeAtualizacao = new Cookie("TOKEN-DE-ATUALIZACAO", tokens.getTokenDeAtualizacao());

    cookieComTokenDeAcesso.setMaxAge(jwtPropriedades.getDuracaoDoTokenDeAcesso());
    cookieComTokenDeAcesso.setHttpOnly(true);
    cookieComTokenDeAcesso.setSecure(false);
    cookieComTokenDeAcesso.setPath("/");

    cookieComTokenDeAtualizacao.setMaxAge(jwtPropriedades.getDuracaoDoTokenDeAtualizacao());
    cookieComTokenDeAtualizacao.setHttpOnly(true);
    cookieComTokenDeAtualizacao.setSecure(false);
    cookieComTokenDeAtualizacao.setPath("/");

    response.addCookie(cookieComTokenDeAcesso);
    response.addCookie(cookieComTokenDeAtualizacao);

    return ResponseEntity.ok("OK");
  }
}