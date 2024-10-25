package com.src.controlador;

import com.src.details.UserDetailsImp;
import com.src.dto.EntidadeId;
import com.src.dto.funcionario.AdicionarFuncionario;
import com.src.dto.funcionario.FuncionarioAdicionado;
import com.src.dto.usuario.CadastrarUsuario;
import com.src.model.entidades.FuncionarioEntidade;
import com.src.model.repositorio.FuncionarioRepositorio;
import com.src.services.AuthService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FuncionarioControllerTest {

  @Autowired
  private AuthService authService;

  @Autowired
  private FuncionarioController funcionarioController;

  @Autowired
  private FuncionarioRepositorio funcionarioRepositorio;

  private UserDetailsImp usuario;

  @BeforeAll
  public void init() {
    var usuarioCad = authService.cadastrar(new CadastrarUsuario("nome", "email@email.com", "senha"));
    usuario = new UserDetailsImp(usuarioCad.id(), usuarioCad.nome());
  }

  @Test
  public void deve_adicionar() {
    AdicionarFuncionario adicionarFunc = new AdicionarFuncionario(
        "funcionario nome",
        new BigDecimal(123456.00),
        LocalDate.now()
    );

    FuncionarioAdicionado funcionarioAd = funcionarioController.adicionar(usuario, adicionarFunc);

    Assertions.assertNotNull(funcionarioAd.id());
    Assertions.assertEquals(adicionarFunc.getNome(), funcionarioAd.nome());
  }

  @Test
  public void deve_remover() {

    AdicionarFuncionario adicionarFunc = new AdicionarFuncionario(
        "funcionario nome",
        new BigDecimal(123456.00),
        LocalDate.now()
    );

    FuncionarioAdicionado funcionarioAdicionado = funcionarioController.adicionar(usuario, adicionarFunc);
    funcionarioController.remover(usuario, new EntidadeId<>(funcionarioAdicionado.id()));

    Optional<FuncionarioEntidade> funcionarioDeletado = funcionarioRepositorio.findById(funcionarioAdicionado.id());
    Assertions.assertTrue(funcionarioDeletado.isEmpty());
  }

  @Test
  public void deve_pesquisar() {

    AdicionarFuncionario funcionario_marcelo = new AdicionarFuncionario(
        "Marcelo",
        new BigDecimal(123456.00),
        LocalDate.now()
    );

    AdicionarFuncionario funcionario_davi = new AdicionarFuncionario(
        "Davi",
        new BigDecimal(123456.00),
        LocalDate.now()
    );

    AdicionarFuncionario funcionario_daniela = new AdicionarFuncionario(
        "Daniela",
        new BigDecimal(123456.00),
        LocalDate.now()
    );

    funcionarioController.adicionar(usuario, funcionario_davi);
    funcionarioController.adicionar(usuario, funcionario_daniela);
    funcionarioController.adicionar(usuario, funcionario_marcelo);

    List<FuncionarioEntidade> funcionarios_que_comacam_com_a_letra_d = funcionarioController.pesquisarFuncionario(
        usuario,
        "D"
    );

    List<FuncionarioEntidade> marcelo_pesquisa = funcionarioController.pesquisarFuncionario(
        usuario,
        "Marcelo"
    );

    Assertions.assertEquals(2, funcionarios_que_comacam_com_a_letra_d.size());
    Assertions.assertTrue(marcelo_pesquisa.size() == 1);
    Assertions.assertEquals(marcelo_pesquisa.get(0).getNome(), funcionario_marcelo.getNome());
  }

  @Test
  public void deve_buscar_pelo_id() {
    AdicionarFuncionario funcionario_marcelo = new AdicionarFuncionario(
        "Marcelo",
        new BigDecimal(123456.00),
        LocalDate.now()
    );

    FuncionarioAdicionado funcionarioAdicionado = funcionarioController.adicionar(usuario, funcionario_marcelo);
    FuncionarioEntidade buscaPeloId = funcionarioController.buscarPeloId(usuario, funcionarioAdicionado.id());

    Assertions.assertNotNull(buscaPeloId.getId());
    Assertions.assertNotNull(buscaPeloId.getNome());
    Assertions.assertNotNull(buscaPeloId.getDataAdmissao());
    Assertions.assertNotNull(buscaPeloId.getDataDeCriacao());

    Assertions.assertNotNull(buscaPeloId.getId());
    Assertions.assertEquals(funcionarioAdicionado.nome(), buscaPeloId.getNome());
  }

  @Test
  public void deve_retornar_funcionarios_de_usuario_autenticado() {
    List<AdicionarFuncionario> funcionarios = List.of(
        new AdicionarFuncionario("Mario", new BigDecimal(123456.00), LocalDate.now()),
        new AdicionarFuncionario("Jose", new BigDecimal(123456.00), LocalDate.now()),
        new AdicionarFuncionario("Alex", new BigDecimal(123456.00), LocalDate.now()),
        new AdicionarFuncionario("Roma", new BigDecimal(123456.00), LocalDate.now()),
        new AdicionarFuncionario("Julio", new BigDecimal(123456.00), LocalDate.now())
    );

    funcionarios.forEach((funcionario) -> funcionarioController.adicionar(usuario, funcionario));

    List<FuncionarioEntidade> funcionarioDeUsuario = funcionarioController
        .retornarFuncionariosDeUsuario(usuario, 1, Optional.of(15));

    Assertions.assertEquals(funcionarios.size(), funcionarioDeUsuario.size());
  }
}
