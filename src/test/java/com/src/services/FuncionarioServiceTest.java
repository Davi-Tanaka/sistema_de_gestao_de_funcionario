package com.src.services;

import com.src.dto.funcionario.AdicionarFuncionario;
import com.src.dto.funcionario.FuncionarioAdicionado;
import com.src.model.entidades.FuncionarioEntidade;
import com.src.model.entidades.UsuarioEntidade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.src.model.repositorio.FuncionarioRepositorio;
import com.src.model.repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@Transactional
public class FuncionarioServiceTest {

  @Autowired
  private FuncionarioService funcionarioService;

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private FuncionarioRepositorio funcionarioRepositorio;

  private UsuarioEntidade usuario;

  @BeforeAll
  public void init() {
    usuario = usuarioRepositorio.save(
        UsuarioEntidade.builder()
            .nome("ab")
            .email("a@b.com")
            .senha("ab")
            .build()
    );
  }

  @Test
  public void deve_adicionar_funcionario() {
    FuncionarioAdicionado fa = this.funcionarioService.adicionar(usuario.getId(), new AdicionarFuncionario(
        "Nome de funcionario",
        new BigDecimal(1235),
        LocalDate.now()
    ));

    Assertions.assertNotNull(fa.id());
    Assertions.assertNotNull(fa.nome());
    Assertions.assertNotNull(fa.dataCriacao());
  }

  @Test
  public void deve_remover_funcionario() {
    FuncionarioAdicionado fa = this.funcionarioService.adicionar(usuario.getId(), new AdicionarFuncionario(
        "Nome de funcionario",
        new BigDecimal(1235),
        LocalDate.now()
    ));

    this.funcionarioService.remover(usuario.getId(), fa.id());

    Optional<FuncionarioEntidade> entidadeRemovida = this.funcionarioRepositorio.findById(fa.id());
    Assertions.assertTrue(entidadeRemovida.isEmpty());
  }

  @Test
  public void deve_pesquisar_funcionario() {
    
    FuncionarioAdicionado antonio = this.funcionarioService.adicionar(usuario.getId(), new AdicionarFuncionario(
        "antonio",
        new BigDecimal(1235),
        LocalDate.now()
    ));

    FuncionarioAdicionado enzo = this.funcionarioService.adicionar(usuario.getId(), new AdicionarFuncionario(
        "enzo",
        new BigDecimal(12355),
        LocalDate.now()
    ));

    List<FuncionarioEntidade> pesquisarEnzo = this.funcionarioRepositorio.pesquisarFuncionarioDeUsuarioPeloNome(
        enzo.nome(), 
        usuario.getId(),
        5
    );

    List<FuncionarioEntidade> pesquisarAntonio = this.funcionarioRepositorio.pesquisarFuncionarioDeUsuarioPeloNome(
        antonio.nome(),
        usuario.getId(),
        5
    );
        
    Assertions.assertEquals(pesquisarAntonio.size(), 1);
    Assertions.assertEquals(pesquisarEnzo.size(), 1);
    
    Assertions.assertEquals(pesquisarAntonio.get(0).getNome(), antonio.nome());
    Assertions.assertEquals(pesquisarEnzo.get(0).getNome(), enzo.nome());
  }
}
