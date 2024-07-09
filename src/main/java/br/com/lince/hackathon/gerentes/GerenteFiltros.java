package br.com.lince.hackathon.gerentes;

public class GerenteFiltros {
    private final String nome;
    private final String documento;
    private final String cidade;
    private final String estado;

    public GerenteFiltros(String nome, String documento, String cidade, String estado) {
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
