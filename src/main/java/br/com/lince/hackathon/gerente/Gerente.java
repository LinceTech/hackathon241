package br.com.lince.hackathon.gerente;

import br.com.lince.hackathon.endereco.Endereco;

import java.time.LocalDate;
import java.util.Objects;

public class Gerente {

    private String cpf;
    private String nome;
    private int telefone;
    private String email;
    private LocalDate dataContratacao;
    private double percentualComissao;
    private Endereco endereco;

    public Gerente(final String cpf, final String nome, final int telefone, final String email, final LocalDate dataContratacao, final double percentualComissao, final Endereco endereco) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.dataContratacao = dataContratacao;
        this.percentualComissao = percentualComissao;
        this.endereco = new Endereco(endereco.getBairro(), endereco.getCep(), endereco.getCidade(), endereco.getEstado(), endereco.getNumeroResidencia(), endereco.getRua());
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public int getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gerente gerente = (Gerente) o;
        return Objects.equals(cpf, gerente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }

    @Override
    public String toString() {
        return "Gerente{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", telefone=" + telefone +
                ", email='" + email + '\'' +
                ", dataContratacao=" + dataContratacao +
                ", percentualComissao=" + percentualComissao +
                ", endereco=" + endereco +
                '}';
    }
}
