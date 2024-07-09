package br.com.lince.hackathon.gerentes;

import java.sql.Date;

public class Gerentes {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String cidade;
    private String estado;
    private float percentualComissao;
    private Date dataContratacao;

    public Gerentes() {
    }

    public Gerentes(int id, String nome, String cpf, String telefone, String email, String cidade, String estado, float percentualComissao, Date dataContratacao) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.cidade = cidade;
        this.estado = estado;
        this.percentualComissao = percentualComissao;
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

    public float getPercentualComissao() {
        return percentualComissao;
    }

    public void setPercentualComissao(float percentualComissao) {
        this.percentualComissao = percentualComissao;
    }

    public Date getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(Date dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    @Override
    public String toString() {
        return "Gerentes{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", percentualComissao=" + percentualComissao +
                ", dataContratacao=" + dataContratacao +
                '}';
    }
}
