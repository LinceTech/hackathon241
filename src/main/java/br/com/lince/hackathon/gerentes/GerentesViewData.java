package br.com.lince.hackathon.gerentes;

import java.util.List;

public class GerentesViewData {
    private final List<Gerentes> gerentes;
    private final String nome;
    private final String cpf;
    private final String cidade;
    private final String estado;

    public GerentesViewData(List<Gerentes> gerentes, String nome, String cpf, String cidade, String estado) {
        this.gerentes = gerentes;
        this.nome = nome;
        this.cpf = cpf;
        this.cidade = cidade;
        this.estado = estado;
    }

    public List<Gerentes> getGerentes() {
        return gerentes;
    }
}
