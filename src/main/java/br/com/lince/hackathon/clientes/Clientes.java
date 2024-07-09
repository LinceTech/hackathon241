package br.com.lince.hackathon.clientes;

import java.time.LocalDate;
import java.util.HashMap;

public class Clientes {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private int ddd;
    private long telefone;
    private String email;
    private int cep;
    private String cidade;
    private String estado;
    private String bairro;
    private String rua;
    private int numero;
    private HashMap<String, String> erros;

    public Clientes() {
    }

    public Clientes(
            Long id,
            String nome,
            String cpf,
            LocalDate dataNascimento,
            int ddd,
            int telefone,
            String email,
            int cep,
            String cidade,
            String estado,
            String bairro,
            String rua,
            int numero
    ) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.ddd = ddd;
        this.telefone = telefone;
        this.email = email;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
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

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public HashMap<String, String> getErros() {
        return erros;
    }

    public void setErros(HashMap<String, String> erros) {
        this.erros = erros;
    }
}
