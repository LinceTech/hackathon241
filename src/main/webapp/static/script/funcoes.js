$(function(){
    if ($('#gerenteForm').length) {
        $('#nr_cpf, .formataCpf').mask('000.000.000-00')
        $('#nr_telefone, .formataTel').mask('(00) 00000-0000')
        $('#pc_comissao, .formataCom').mask('00.00')
    }
})

function buscarCEP() {
    var cep = document.getElementById('cepInput').value.trim();
    var nm_cidade = document.getElementById('nm_cidade');
    var nm_estado = document.getElementById('nm_estado');
    var nm_bairro = document.getElementById('nm_bairro');
    var nm_rua = document.getElementById('nm_rua');
    var nr_residencia = document.getElementById('nr_residencia');
    var url = 'https://viacep.com.br/ws/' + cep + '/json';
    var target = document.getElementById('resultadoCEP');

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            return response.json();
        })
        .then(data => {
            console.log(data)
            nm_cidade.value = data.localidade;
            nm_estado.value = data.uf;
            nm_bairro.value = data.bairro;
            nm_rua.value = data.logradouro;
            nr_residencia.value = data.siafi;
        })
        .catch(error => {
            // Trata erros de requisição
            console.error('Erro na requisição:', error);
        });
}

$(document).ready(function(){
    $("#tabelaClientes").tablesorter();
});