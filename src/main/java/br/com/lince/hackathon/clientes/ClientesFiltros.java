package br.com.lince.hackathon.clientes;

import java.time.LocalDate;

public class ClientesFiltros {
    private final String nome;
    private final String cidade;

    public ClientesFiltros(String nome, String cidade) {
        this.nome = nome;
        this.cidade = cidade;
    }

    public String getnome() {
        return nome;
    }

    public String getcidade() {
        return cidade;
    }


}
