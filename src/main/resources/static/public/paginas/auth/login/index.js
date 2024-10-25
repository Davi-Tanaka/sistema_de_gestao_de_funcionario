const form = document.getElementById('login-form');

form.addEventListener('submit', logar);

/**
 * Verifica se email e senha são validos
 * @param {FormData} form
 * @returns {boolean}
 */
function formularioEhValido(form) {
  const obj = Object.fromEntries(form);

  if(obj.email.length < 3) {
    return false;
  }

  if(obj.senha.length <= 1) {
    return false;
  }

  return true;
}

/**
  * Autentica o usuário no sistema.
  * @param {SubmitEvent} e 
  */
async function logar(e) {
  e.preventDefault();

  const URL = "/auth/login"
  const formData = new FormData(form);
  const formularioValido = formularioEhValido(formData);

  if(formularioValido) {
    const jsonBody = JSON.stringify(Object.fromEntries(formData));
    const loginResponse = await fetch(URL, { 
      method: "POST", 
      body: jsonBody, 
      credentials: 'include',
      headers: {
        "Content-Type": "application/json"
      }
    });

    if(loginResponse.status == 200) {
      limparCampostDeFormulario(form);
      window.location.href = "/";
    }
  }
}

function limparCampostDeFormulario(formulario) {
  formulario.querySelectorAll("input").forEach(inp => inp.value = "");
}
