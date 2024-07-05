package br.com.lince.hackathon.clientes;

public class ClienteFiltros {
    private final String nome;
    private final String documento;
    private final String cidade;
    private final String estado;

    public ClienteFiltros(String nome, String documento, String cidade, String estado) {
        this.nome = nome;
        this.documento = documento;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }
}
