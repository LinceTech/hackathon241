package br.com.lince.hackathon.gerente;

import br.com.lince.hackathon.foo.Foo;

import java.util.Objects;

public class Gerente {
    public Gerente() {
    }

    public Gerente(String nome, long cpf, long telefone, String email, String cidade, String estado, double percentualComissao, int dataContratacao, String dataContratacaoAlfa, String cpfAlfa, String telefoneAlfa) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.cidade = cidade;
        this.estado = estado;
        this.percentualComissao = percentualComissao;
        this.dataContratacao = dataContratacao;
        this.dataContratacaoAlfa = dataContratacaoAlfa;
        this.telefoneAlfa = telefoneAlfa;
    }

    private String nome;
    private long cpf;
    private String cpfAlfa;
    private long telefone;
    private String telefoneAlfa;
    private String email;
    private String cidade;
    private String estado;
    private double percentualComissao;
    private int dataContratacao;
    private String dataContratacaoAlfa;

    public String getDataContratacaoAlfa() {
        return dataContratacaoAlfa;
    }

    public String getCpfAlfa() {
        return cpfAlfa;
    }

    public void setCpfAlfa(String cpfAlfa) {
        this.cpfAlfa = cpfAlfa;
    }

    public void setDataContratacaoAlfa(String dataContratacaoAlfa) {
        this.dataContratacaoAlfa = dataContratacaoAlfa;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPercentualComissao(double percentualComissao) {
        this.percentualComissao = percentualComissao;
    }

    public void setDataContratacao(int dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public String getNome() {
        return nome;
    }

    public long getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public long getTelefone() {
        return telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public int getDataContratacao() {
        return dataContratacao;
    }

    public String getTelefoneAlfa() {
        return telefoneAlfa;
    }

    public void setTelefoneAlfa(String telefoneAlfa) {
        this.telefoneAlfa = telefoneAlfa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gerente gerente = (Gerente) o;

        return Objects.equals(nome,gerente.nome) && cpf == gerente.cpf && telefone == gerente.telefone&& Objects.equals(email,gerente.email) && Objects.equals(cidade,gerente.cidade) && Objects.equals(estado,gerente.estado) && percentualComissao == gerente.percentualComissao && dataContratacao == gerente.dataContratacao;
    }

    //nome, cpf, telefone, email, cidade, estado, percentualComissao, dataContratacao
    @Override
    public int hashCode() {
        return Objects.hash(nome, cpf, telefone, email, cidade, estado, percentualComissao, dataContratacao);
    }

    @Override
    public String toString() {
        return "Gerente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone=" + telefone +
                ", email='" + email + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", percentualComissao=" + percentualComissao +
                ", dataContratacao=" + dataContratacao +
                '}';
    }

}
