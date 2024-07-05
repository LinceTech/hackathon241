package br.com.lince.hackathon.endereco;

import java.util.Objects;

public class Endereco {

    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
    private int numeroResidencia;
    private String rua;

    public Endereco(final String bairro, final String cep, final String cidade, final String estado, final int numeroResidencia, final String rua) {
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.numeroResidencia = numeroResidencia;
        this.rua = rua;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getBairro() {
        return bairro;
    }

    public String getRua() {
        return rua;
    }

    public int getNumeroResidencia() {
        return numeroResidencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(cep, endereco.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cep);
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "cep='" + cep + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                ", numeroResidencia=" + numeroResidencia +
                '}';
    }
}
