package br.com.lince.hackathon.cliente;

import br.com.lince.hackathon.endereco.Endereco;

import java.time.LocalDate;
import java.util.Objects;

public class Cliente {

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private int telefone;
    private String email;
    private Endereco endereco;

    public Cliente(final String nome, final String cpf, final LocalDate dataNascimento, final int telefone, final String email, final Endereco endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
        this.endereco = new Endereco(endereco.getBairro(), endereco.getCep(), endereco.getCidade(), endereco.getEstado(), endereco.getNumeroResidencia(), endereco.getRua());
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public int getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", telefone=" + telefone +
                ", email='" + email + '\'' +
                ", endereco=" + endereco +
                '}';
    }
}
