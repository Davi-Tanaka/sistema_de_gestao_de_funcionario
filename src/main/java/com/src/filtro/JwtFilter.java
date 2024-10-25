package com.src.filtro;

import com.src.details.UserDetailsImp;
import com.src.details.UserDetailsServiceImp;
import com.src.dto.autenticacao.CorpoDoTokenDeAcesso;
import com.src.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private JwtService jwtService;  
  private UserDetailsServiceImp userDetailsServiceImp;
  
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, 
      HttpServletResponse response, 
      FilterChain filterChain
  ) throws ServletException, IOException {
    Cookie[] cookies = request.getCookies();
    Cookie tokenDeAcesso = null;

    if(cookies != null && cookies.length > 0) {
      List<Cookie> filterResult = Arrays
          .stream(cookies)
          .filter(e -> e.getName().equals("TOKEN-DE-ACESSO"))
          .collect(Collectors.toList());

      if(!filterResult.isEmpty()) {
        tokenDeAcesso = filterResult.get(0);
      }
      
      if(tokenDeAcesso != null) {
        boolean tokenValido = jwtService.tokenDeAcessoEhValida(tokenDeAcesso.getValue());

        if(tokenValido && SecurityContextHolder.getContext().getAuthentication() == null) {
          CorpoDoTokenDeAcesso corpoDeTokenDeAcesso = jwtService.lerTokenDeAcesso(
            tokenDeAcesso.getValue()
          );

          UserDetailsImp userDetails = (UserDetailsImp) userDetailsServiceImp.carregarUsuarioPeloId(
              corpoDeTokenDeAcesso.getId()
          );
 
          UsernamePasswordAuthenticationToken tokenDeAutenticao = new UsernamePasswordAuthenticationToken(
              userDetails,
              null, 
              userDetails.getAuthorities()
          );
          
          SecurityContextHolder.getContext().setAuthentication(tokenDeAutenticao);
        }
      }
    }

    doFilter(request, response, filterChain);
  }
}
