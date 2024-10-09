const form = document.getElementById('#login-form');

form.addEventListener('submit', logar);
/**
 * Verifica se nome, email e senha são validos
 * @param {FormData} formData
 * @returns {boolean}
 */
function formularioEhValido(formData) {
  const obj = Object.from(formData);

  if(obj.nome.length < 3) {
    return false;
  }


  if(obj.email.length < 3) {
    return false;
  }

  if(obj.senha.length <= 1) {
    return false;
  }
}

/**
  * Autentica o usuário no sistema.
  * @param {SubmitEvent} e 
  */
function logar(e) {
  e.preventDefault();

  const formularioValido = formularioEhValido(form);
}
