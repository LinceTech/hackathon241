package br.com.lince.hackathon.clientes;

import java.util.List;



public class ClientesViewData {
    private final List<Clientes> clientes;
    private final String nome;
    private final String cidade;
    private final int numeroPagina;

    public ClientesViewData(List<Clientes> clientes, String nome, String cidade, int numeroPagina) {
        this.clientes = clientes;
        this.nome = nome;
        this.cidade = cidade;
        this.numeroPagina = numeroPagina;
    }

    public List<Clientes> getClientes() {
        return clientes;
    }

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    public int getNumeroPagina() {
        return numeroPagina;
    }
}