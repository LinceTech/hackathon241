package br.com.lince.hackathon.gerentes;

import java.util.HashMap;
import java.util.List;

public class GerentesViewData {
    private final List<Gerentes> gerentes;
    private final String nome;
    private final String cpf;
    private final String cidade;
    private final String estado;
    private final int numeroPagina;

    public GerentesViewData(
            List<Gerentes> gerentes,
            String nome,
            String cpf,
            String cidade,
            String estado,
            int numeroPagina
    ) {
        this.gerentes = gerentes;
        this.nome = nome;
        this.cpf = cpf;
        this.cidade = cidade;
        this.estado = estado;
        this.numeroPagina = numeroPagina;
    }

    public List<Gerentes> getGerentes() {
        return gerentes;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public int getNumeroPagina() {
        return numeroPagina;
    }
}
