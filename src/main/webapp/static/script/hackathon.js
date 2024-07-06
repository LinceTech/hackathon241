function limpa_formulario_cep() {
    //Limpa valores do formulario de cep.
    if (document.getElementById('cep')) document.getElementById('cep').value = "";
    if (document.getElementById('estado')) document.getElementById('estado').value = "";
    if (document.getElementById('cidade')) document.getElementById('cidade').value = "";
    if (document.getElementById('bairro')) document.getElementById('bairro').value = "";
    if (document.getElementById('rua')) document.getElementById('rua').value = "";
}

function meu_callback(conteudo) {
    if (!("erro" in conteudo)) {
        //Atualiza os campos com os valores.
        if (document.getElementById('estado')) document.getElementById('estado').value = conteudo.uf;
        if (document.getElementById('cidade')) document.getElementById('cidade').value = conteudo.localidade;
        if (document.getElementById('bairro')) document.getElementById('bairro').value = conteudo.bairro;
        if (document.getElementById('rua')) document.getElementById('rua').value = conteudo.logradouro;
    } else {
        //CEP não Encontrado.
        limpa_formulario_cep();
        alert("CEP não encontrado.");
    }
}

function pesquisacep(valor) {

    //Nova variavel "cep" somente com dígitos.
    let cep = valor.replace(/\D/g, '');

    //Verifica se campo cep possui valor informado.
    if (cep !== "") {

        //Expressão regular para validar o CEP.
        let validacep = /^[0-9]{8}$/;

        //Valida o formato do CEP.
        if(validacep.test(cep)) {

            //Preenche os campos com "..." enquanto consulta webservice.
            if (document.getElementById('estado')) document.getElementById('estado').value = "...";
            if (document.getElementById('cidade')) document.getElementById('cidade').value = "...";
            if (document.getElementById('bairro')) document.getElementById('bairro').value = "...";
            if (document.getElementById('rua')) document.getElementById('rua').value = "...";

            //Cria um elemento javascript.
            let script = document.createElement('script');

            //Sincroniza com o callback.
            script.src = 'https://viacep.com.br/ws/'+ cep + '/json/?callback=meu_callback';

            //Insere script no documento e carrega o conteúdo.
            document.body.appendChild(script);

        } else {
            //cep é invalido.
            limpa_formulario_cep();
            alert("Formato de CEP invalido.");
        }
    } else {
        //cep sem valor, limpa formulario.
        limpa_formulario_cep();
    }
}