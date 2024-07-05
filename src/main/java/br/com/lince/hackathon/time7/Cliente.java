package br.com.lince.hackathon.time7;

import br.com.lince.hackathon.foo.Foo;

import java.util.Objects;

public class Cliente {
    private int    id           ;
    private String nome         ;
    private int    cpf          ;
    private int    dtNascimento ;
    private int    telefone     ;
    private String email        ;
    private int    cep          ;
    private String cidade       ;
    private String estado       ;
    private String bairro       ;
    private String rua          ;
    private int    numero       ;

    public Cliente() {
    }

    public Cliente(int    id           ,
                   String nome         ,
                   int    cpf          ,
                   int    dtNascimento ,
                   int    telefone     ,
                   String email        ,
                   int    cep          ,
                   String cidade       ,
                   String estado       ,
                   String bairro       ,
                   String rua          ,
                   int    numero       ) {

        this.id           = id           ;
        this.nome         = nome         ;
        this.cpf          = cpf          ;
        this.dtNascimento = dtNascimento ;
        this.telefone     = telefone     ;
        this.email        = email        ;
        this.cep          = cep          ;
        this.cidade       = cidade       ;
        this.estado       = estado       ;
        this.bairro       = bairro       ;
        this.rua          = rua          ;
        this.numero       = numero       ;
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

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public int getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(int dtNascimento) {
        this.dtNascimento = dtNascimento;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cliente cliente = (Cliente) o;
        return id == cliente.id && Objects.equals(nome        , cliente.nome        )
                                && Objects.equals(cpf         , cliente.cpf         )
                                && Objects.equals(dtNascimento, cliente.dtNascimento)
                                && Objects.equals(telefone    , cliente.telefone    )
                                && Objects.equals(email       , cliente.email       )
                                && Objects.equals(cep         , cliente.cep         )
                                && Objects.equals(cidade      , cliente.cidade      )
                                && Objects.equals(estado      , cliente.estado      )
                                && Objects.equals(bairro      , cliente.bairro      )
                                && Objects.equals(rua         , cliente.rua         )
                                && Objects.equals(numero      , cliente.numero      );
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cpf, dtNascimento, telefone, email, cep, cidade, estado, bairro, rua, numero);
    }

    @Override
    public String toString() {
        return "Cliente {" +
                "  id           = "  + id           +
                ", nome         = '" + nome         + '\'' +
                ", cpf          = "  + cpf          +
                ", dtNascimento = "  + dtNascimento +
                ", telefone     = "  + telefone     +
                ", email        = '" + email        + '\'' +
                ", cep          = "  + cep          +
                ", cidade       = '" + cidade       + '\'' +
                ", estado       = '" + estado       + '\'' +
                ", bairro       = '" + bairro       + '\'' +
                ", rua          = '" + rua          + '\'' +
                ", numero       = "  + numero       +
                '}';
    }
}
