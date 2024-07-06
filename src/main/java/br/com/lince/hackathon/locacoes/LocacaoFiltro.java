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

    public String getNome() {
        return nome != null ? nome : "";
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf != null ? cpf : "";
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCidade() {
        return cidade != null ? cidade : "";
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado != null ? estado : "";
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
