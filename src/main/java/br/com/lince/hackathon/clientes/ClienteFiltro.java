package br.com.lince.hackathon.clientes;

public class ClienteFiltro {
    private String nome;
    private String cidade;
    private String data_nascimento;

    public ClienteFiltro(String nome, String cidade, String data_nascimento) {
        this.nome = nome;
        this.cidade = cidade;
        this.data_nascimento = data_nascimento;
    }

    public String getNome() {
        return nome != null ? nome : "";
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade  != null ? cidade : "";
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getData_nascimento() {
        return data_nascimento  != null ? data_nascimento : "";
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
}
