package br.com.lince.hackathon.gerente;

import br.com.lince.hackathon.foo.Foo;

import java.util.Objects;

public class Gerente {
    /**
     * Construtor vazio, necessário para uso com JDBI mapper
     */
    public Gerente() {
    }

    /**
     * Construir uma instância de Foo.
     *
     * @param nome valor para nome
     * @param cpf valor para cpf
     * @param telefone valor para telefone
     * @param email valor para email
     * @param cidade valor para cidade
     * @param estado valor para estado
     * @param percentualComissao valor para percentualComissao
     * @param dataContratacao valor para dataContratacao
     */

    public Gerente(String nome, int cpf, int telefone, String email, String cidade, String estado, double percentualComissao, int dataContratacao) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.cidade = cidade;
        this.estado = estado;
        this.percentualComissao = percentualComissao;
        this.dataContratacao = dataContratacao;
    }

    private String nome;
    private int cpf;
    private int telefone;
    private String email;
    private String cidade;
    private String estado;
    private double percentualComissao;
    private int dataContratacao;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(int telefone) {
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

    public int getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public int getTelefone() {
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
