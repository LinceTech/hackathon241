package br.com.lince.hackathon.endereco;

import java.util.Objects;

public class Endereco {

    private String cep;
    private String cidade;
    private String estado;
    private String bairro;
    private String rua;
    private int numeroResidencia;

    public Endereco(final String cep, final String cidade, final String estado, final String bairro, final String rua, final int numeroResidencia) {
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.bairro = bairro;
        this.rua = rua;
        this.numeroResidencia = numeroResidencia;
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
