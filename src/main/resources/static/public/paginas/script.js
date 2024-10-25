const form_adicionarFuncionario = document.querySelector("#adicionar-funcionario");

const btn_abrirTelaFuncionario = document.querySelector("#adicionar-funcionario-btn");
const btn_fecharTelaFuncionario = document.querySelector("#fechar-tela-adicionar-funcionario-btn");
const btn_removerFuncionario = () => document.querySelectorAll(".remover-funcionario-button");
const btn_editarFuncionario = document.querySelectorAll(".editar-funcionario-button");

const tabela_funcionario__corpo = document.querySelector("#funcionarios-tabela__body");
const input_pesquisarFuncionario = document.querySelector("#pesquisar-funcionario-input");
const salarioSomatorio = document.querySelector("#salario-somatorio");

btn_fecharTelaFuncionario.addEventListener("click", fecharTelaAdicionarFuncionario);

form_adicionarFuncionario.addEventListener("submit", adicionarFuncionario);
btn_abrirTelaFuncionario.addEventListener("click", abrirTelaAdicionarFuncionario);

input_pesquisarFuncionario.addEventListener("input", e => pesquisarPorFuncionario(e.currentTarget.value));

const adicionarEventoAosBotoesDeExcluirFuncionario = () => btn_removerFuncionario().forEach(elemento => {
  elemento.addEventListener("click", removerFuncionario);
})


adicionarEventoAosBotoesDeExcluirFuncionario();

async function buscarPeloId(id) {
  return await fetch(`/funcionario/id/${id}`,{ crendentials: "same-site" }).then(e => e.json());
}

async function adicionarFuncionario(e) {

  e.preventDefault();

  const form = new FormData(form_adicionarFuncionario);
  const obj = Object.fromEntries(form);
  const json = JSON.stringify(obj);

  if(!obj.nome || !obj.salario || !obj.dataAdmissao) {
    return;
  }

  const res = await fetch("/funcionario/adicionar", {
    method: "post",
    crendentials: "same-origin",
    body: json,
    headers: {
      "Content-Type": "application/json"
    }
  })

  if(res.status == 201) {
    const obj = await res.json();
    const funcionario = await buscarPeloId(obj.id);
    var linha = gerarLinhaDaTabelaDeFuncionario(funcionario);

    tabela_funcionario__corpo.innerHTML += linha;
    
    const salarios = (await pesquiseFuncionarioERetornevalor(input_pesquisarFuncionario.value || ""))
      .map(e => e.salario);

    atualizarSomatorioDeSalarios(salarios);
    adicionarEventoAosBotoesDeExcluirFuncionario();
  }

  form_adicionarFuncionario.querySelectorAll(" * input").forEach(el => el.value = "");
}

function abrirTelaAdicionarFuncionario() {
  form_adicionarFuncionario.classList.remove("d-none");
}


function fecharTelaAdicionarFuncionario() {
  form_adicionarFuncionario.classList.add("d-none");
}


async function pesquisarPorFuncionario(inputValor) {
  tabela_funcionario__corpo.innerHTML = "";

  const resultado = await pesquiseFuncionarioERetornevalor(inputValor || "")

  if(resultado && resultado?.length > 0) {
    resultado.forEach(e => {
      tabela_funcionario__corpo.innerHTML += gerarLinhaDaTabelaDeFuncionario(e);
    })
  }

  atualizarSomatorioDeSalarios(resultado.map(e => e.salario));
}

async function pesquiseFuncionarioERetornevalor(valor) {
  let resultado;

  if(!valor) {
    resultado = await fetch(`/funcionario/adicionados?pagina=1`, { credentials: "same-origin" }).then(e => e.json()); 
  } else {
    resultado = await fetch(`/funcionario/pesquisar?nome=${valor}`, { credentials: "same-origin" }).then(e => e.json()); 
  }

  return resultado
}

/**
 * @param {number[]} listaSalario 
 */
function atualizarSomatorioDeSalarios(listaSalario) {
  let resultado;

  if(listaSalario?.length > 0) {
    resultado = listaSalario.reduce((acumulado, valorAtual) => acumulado + valorAtual);
  }

  salarioSomatorio.innerHTML = resultado || 0;
}

async function removerFuncionario(evt) {
  const linha = evt.currentTarget.parentElement.parentElement;
  const id = linha.getAttribute("data-id");

  if(id != null) {
    const res = await fetch("/funcionario/remover", {
      method: "delete",
      crendentials: "same-origin",
      body: JSON.stringify({ id }),
      headers: {
        "Content-Type": "application/json"
      }
    });

    if(res.status == 200) {
      linha.remove();
      adicionarEventoAosBotoesDeExcluirFuncionario();
    }
  }

  const arr = await pesquiseFuncionarioERetornevalor(input_pesquisarFuncionario.value);
  atualizarSomatorioDeSalarios(arr.map(e => e.salario));
}

/**
  *
  * @param {{id: string, salario: number, dataAdmissao: string, dataDeCriacao: string }} funcionario
  *
  */
function gerarLinhaDaTabelaDeFuncionario(funcionario) {
  const formatarData = (str) => {
    return new Date(str.replace('-', "/")).toLocaleString("pt-br")
      .split(",")[0];
  };

  const formatarDataComTempo = (str) => {
    return new Date(str).toLocaleString("pt-br") .replaceAll(",", "");
  };

  return `
    <tr data-id="${funcionario.id}">
      <td class="text-center" scope="row">${funcionario.nome}</td>
      <td class="text-center" scope="row">${funcionario.salario}</td>
      <td class="text-center" scope="row">${formatarData(funcionario.dataAdmissao)}</td>
      <td class="text-center" scope="row">${formatarDataComTempo(funcionario.dataDeCriacao)}</td>

      <td scope="row">
        <button class="mx-auto remover-funcionario-button p-1 border-0 rounded-circle bg-transparent d-flex align-items-center justify-content-center">
          <img src="/public/icons/remover.svg" alt="" width="18" height="18" />
        </button>
      </td>
    </tr>
  `
}
