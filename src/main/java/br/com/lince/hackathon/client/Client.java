package br.com.lince.hackathon.client;

import br.com.lince.hackathon.foo.Foo;

import java.util.Date;
import java.util.Objects;

public class Client {
    /**
     * Construtor vazio, necessário para uso com JDBI mapper
     */
    public Client() {
    }

    public Client(int id, int numero, String nome, Date data_nascimento, String cpf, String telefone, String email, String cep, String estado, String bairro, String cidade, String rua) {
        this.id = id;
        this.numero = numero;
        this.nome = nome;
        this.data_nascimento = data_nascimento;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.cep = cep;
        this.estado = estado;
        this.bairro = bairro;
        this.cidade = cidade;
        this.rua = rua;
    }

    private int id;
    private String nome;
    private String cpf;
    private Date data_nascimento;
    private String telefone;
    private String email;
    private String cep;
    private String cidade;
    private String estado;
    private String bairro;
    private String rua;
    private int numero;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, data_nascimento, telefone, email, cep, cidade, estado, bairro, rua, numero);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", data_nascimento='" + data_nascimento + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", cep='" + cep + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                ", numero=" + numero +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Client client = (Client) obj;

        return id == client.id && Objects.equals(nome, client.nome) && Objects.equals(cpf, client.cpf)
                && Objects.equals(data_nascimento, client.data_nascimento)
                && Objects.equals(telefone, client.telefone)
                && Objects.equals(email, client.email)
                && Objects.equals(cep, client.cep)
                && Objects.equals(cidade, client.cidade)
                && Objects.equals(estado, client.estado)
                && Objects.equals(bairro, client.bairro)
                && Objects.equals(rua, client.rua)
                && numero == client.numero;
    }
}
