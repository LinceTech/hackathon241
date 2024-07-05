function buscarCEP() {
    var cep = document.getElementById('cepInput').value.trim();
    var nm_cidade = document.getElementsByName('nm_cidade');
    var nm_estado = document.getElementsByName('nm_estado');
    var nm_bairro = document.getElementsByName('nm_bairro');
    var nm_rua = document.getElementsByName('nm_rua');
    var nr_residencia = document.getElementsByName('nr_residencia');

    var url = 'https://viacep.com.br/ws/' + cep + '/json';
    var target = document.getElementById('resultadoCEP');

    htmx.fetch(url, {
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Erro ao buscar o CEP.');
        }
    }).then(function (data) {
        nm_cidade.innerHTML = data.localidade;
        nm_estado.innerHTML = data.uf;
        nm_bairro.innerHTML = data.bairro;
        nm_rua.innerHTML = data.logradouro;
        nr_residencia.innerHTML = data.siafi;

    }).catch(function (error) {
        console.error('Erro na requisição:', error);
    });
}
