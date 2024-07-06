package br.com.lince.hackathon.Cliente;

import java.util.Objects;

public class Cliente {

    private String nome;
    private long cpf;
    private int dataNascimento;
    private int telefone;
    private String email;
    private int cep;
    private String cidade;
    private String estado;
    private String bairro;
    private String rua;
    private int numero;



    public Cliente() {
    }

    public Cliente(String nome, long cpf, int dataNascimento, int telefone, String email, int cep, String cidade, String estado, String bairro, String rua, int numero) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public int getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(int dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return cpf == cliente.cpf && dataNascimento == cliente.dataNascimento && telefone == cliente.telefone && cep == cliente.cep && numero == cliente.numero && Objects.equals(nome, cliente.nome) && Objects.equals(email, cliente.email) && Objects.equals(cidade, cliente.cidade) && Objects.equals(estado, cliente.estado) && Objects.equals(bairro, cliente.bairro) && Objects.equals(rua, cliente.rua);
    }

    @Override
    public int hashCode() {
            return Objects.hash(nome, cpf, dataNascimento, telefone, email, cep, cidade, estado, bairro, rua, numero);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cpf=" + cpf +
                ", dataNascimento=" + dataNascimento +
                ", telefone=" + telefone +
                ", email='" + email + '\'' +
                ", cep=" + cep +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                ", numero=" + numero +
                '}';
    }
}
