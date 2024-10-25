package com.src.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.src.details.UserDetailsImp;
import com.src.dto.EntidadeId;
import com.src.dto.funcionario.AdicionarFuncionario;
import com.src.dto.funcionario.FuncionarioAdicionado;
import com.src.model.entidades.FuncionarioEntidade;
import com.src.services.FuncionarioService;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/funcionario")
@RestController
public class FuncionarioController {

  @Autowired
  private FuncionarioService funcionarioService;

  @GetMapping("/adicionados")
  public List<FuncionarioEntidade> retornarFuncionariosDeUsuario(
    @AuthenticationPrincipal UserDetailsImp usuario,
    @RequestParam("pagina") int pagina,
    @RequestParam(value = "quantidade", required = false) Optional<Integer> quantidade
  ) {
    return funcionarioService.retornarFuncionariosDeUsuario(
        usuario.getId(), 
        pagina, 
        quantidade.isPresent() ? quantidade.get() : 15
      );
  }
  
  
  @PostMapping("/adicionar")
  @ResponseStatus(HttpStatus.CREATED)
  public FuncionarioAdicionado adicionar( 
    @AuthenticationPrincipal UserDetailsImp usuario, 
    @RequestBody AdicionarFuncionario corpo 
  ) {
    return funcionarioService.adicionar(usuario.getId(), corpo);
  }
  

  @DeleteMapping("/remover")
  @ResponseStatus(HttpStatus.OK)
  public void remover( 
    @AuthenticationPrincipal UserDetailsImp usuario, 
    @RequestBody EntidadeId<String> corpo
  ) {    
    funcionarioService.remover(usuario.getId(), corpo.id());
  }
  
  
  @GetMapping("/pesquisar")
  public List<FuncionarioEntidade> pesquisarFuncionario(
      @AuthenticationPrincipal UserDetailsImp usuario,
      @RequestParam("nome") @NotBlank String nome
  ) {
    return funcionarioService.pesquisarPeloNome(usuario.getId(), nome);
  }
  
  @GetMapping("/id/{id}")
  public FuncionarioEntidade buscarPeloId(
      @AuthenticationPrincipal UserDetailsImp usuario,
      @PathVariable("id") String id
  ) {
    return this.funcionarioService.buscarPeloIdOrLanceError(id, usuario.getId());
  }
}
