const form = document.querySelector("#cadastrar-form");

form.addEventListener('submit', cadastrar);
/**
 * Verifica se nome, email e senha são validos
 * @param {FormData} form
 * @returns {boolean}
 */
function formularioEhValido(form) {
  const obj = Object.fromEntries(form);

  if(obj.nome.length < 3) {
    return false;
  }

  if(obj.email.length < 3) {
    return false;
  }

  if(obj.senha.length <= 1) {
    return false;
  }

  return true
}

/**
  * Autentica o usuário no sistema.
  * @param {SubmitEvent} e 
  */
async function cadastrar(e) {
  e.preventDefault();

  const URL = "/auth/cadastrar"
  const formData = new FormData(form);
  const formularioValido = formularioEhValido(formData);

  if(formularioValido) {
    const jsonBody = JSON.stringify(Object.fromEntries(formData));
    const cadastroResponse = await fetch(URL, { 
      method: "POST",
      body: jsonBody,
      headers: {
        "Content-Type": "application/json"
      }
    });

    if(cadastroResponse.status == 201) {
      window.location.href = "/auth/login";
    }

    limparCampostDeFormulario(form);
  }
}

function limparCampostDeFormulario(formulario) {
  formulario.querySelectorAll("input").forEach(inp => inp.value = "");
}
