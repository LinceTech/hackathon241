package br.com.lince.hackathon.gerentes;

import java.time.LocalDate;

public class Gerentes {
    int id;
    String nome;
    String cpf;
    String telefone;
    String email;
    String cidade;
    String estado;
    Float percentual;
    LocalDate dataContratacao;

    public Gerentes(){
    }

    public Gerentes(String nome, String cpf, String telefone, String email, String cidade, String estado, Float percentual, LocalDate dataContratacao) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.cidade = cidade;
        this.estado = estado;
        this.percentual = percentual;
        this.dataContratacao = dataContratacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPercentual() {
        return percentual != null ? percentual.toString() : "";
    }

    public void setPercentualDeComissao(Float percentual) {
        this.percentual = percentual;
    }

    public String getDataContratacao() {
        return dataContratacao != null ? dataContratacao.toString() : "";
    }

    public void setDataDeContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }
}
