package br.com.lince.hackathon.locacoes;

public class LocacaoFiltro {
    private String nome;
    private String cpf;
    private String cidade;
    private String estado;

    public LocacaoFiltro(String nome, String cpf, String cidade, String estado) {
        this.nome = nome;
        this.cpf = cpf;
        this.cidade = cidade;
        this.estado = estado;
    }
}
