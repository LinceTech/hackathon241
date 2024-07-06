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

function limpaModelos() {
    let modelo = document.getElementById('modelo');
    while (modelo.children.length > 1) {
        modelo.removeChild(modelo.lastChild);
    }
}

function populaModelos(data) {
    limpaModelos();
    let modelo = document.getElementById('modelo');

    for (let i = 0; i < data.length; i++) {
        let novo = document.createElement("option");
        novo.value = data[i]["code"];
        novo.textContent = data[i]["name"];
        modelo.appendChild(novo);
    }
}

function buscaModelos() {
    const api_url = 'https://fipe.parallelum.com.br/api/v2/cars/brands';
    let marca = document.getElementById('marca').value;

    if (marca !== '') {
        let url_modelos = api_url + '/' + marca + '/models';
        fetch(url_modelos).then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            populaModelos(data);
        })
        .catch(error => {
            limpaModelos();
            console.error('Erro: ', error);
        })
    } else {
        // marca sem valor, limpa combo.
        limpaModelos();
    }

}