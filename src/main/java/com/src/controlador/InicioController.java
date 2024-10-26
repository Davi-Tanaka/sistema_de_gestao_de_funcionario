package com.src.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.src.details.UserDetailsImp;
import com.src.model.entidades.FuncionarioEntidade;
import com.src.services.FuncionarioService;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class InicioController {
  
  @Autowired
  private FuncionarioService funcionarioService;

  @GetMapping("/")
  public ModelAndView retornarPaginaInicial(
    @AuthenticationPrincipal UserDetailsImp usuario, 
    Model model
  ) {
    if(usuario != null) {
      List<FuncionarioEntidade> funcionarioLista = this.funcionarioService.retornarFuncionariosDeUsuario(
          usuario.getId(), 1, 15
      );
      
      List<BigDecimal> listaSalario = funcionarioLista
          .stream()
          .map(e -> e.getSalario())
          .collect(Collectors.toList());
            
      BigDecimal salarioTotal = BigDecimal.ZERO;
      
      for (BigDecimal valor : listaSalario) {
        if (valor != null) {
          salarioTotal = salarioTotal.add(valor);
        }
      } 
      
      model.addAttribute("salariosSomatorio", salarioTotal);
      model.addAttribute("funcionarios", funcionarioLista);
      model.addAttribute("nomeDeUsuario", usuario.getUsername());

      return new ModelAndView("index/index");
    } else {
      return new ModelAndView("redirect:/auth/login");
    }
  }
}
