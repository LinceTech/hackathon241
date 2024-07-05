package br.com.lince.hackathon.gerente;

import java.sql.Date;
import java.time.LocalDate;

/**
 * A classe Foo é apenas um exemplo de 'Data class', nomes da classe a atributos não tem significado algum,
 * representam apenas uma estrutura para armazenar dados de forma combinada, sem lógica ou inteligencia.
 * <br/>
 * Obs.: Em versões mais recentes do Java, essa classe seria criada como um Record.
 */
public class Gerente {
    /**
     * Construtor vazio, necessário para uso com JDBI mapper
     */
    public Gerente() {
    }

    public int getIndativo() {
        return indativo;
    }

    public void setIndativo(int indativo) {
        this.indativo = indativo;
    }

    /**
     * Construir uma instância de Foo.
     *
     * @param cdgerente valor para bar
     * @param nome valor para bas
     * @param cpf valor para boo
     */
    public Gerente(int cdgerente, String nome, String cpf, int telefone, int ddd, String email, String cidade, String estado, double pccomissao, LocalDate dtcontratacao, int indativo) {
        this.cdgerente = cdgerente;
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cidade = cidade;
        this.estado = estado;
        this.pccomissao = pccomissao;
        this.dtcontratacao = dtcontratacao;
        this.indativo = indativo;
        this.ddd = ddd;
    }

    public int getCdgerente() {
        return cdgerente;
    }

    public void setCdgerente(int cdgerente) {
        this.cdgerente = cdgerente;
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

    public double getPccomissao() {
        return pccomissao;
    }

    public void setPccomissao(double pccomissao) {
        this.pccomissao = pccomissao;
    }

    public LocalDate getDtcontratacao() {
        return dtcontratacao;
    }

    public void setDtcontratacao(LocalDate dtcontratacao) {
        this.dtcontratacao = dtcontratacao;
    }

    /**
     * Atributo bar, sem significado algum
     */
    private int cdgerente;

    /**
     * Atributo bar, sem significado algum
     */
    private String nome;

    /**
     * Atributo bar, sem significado algum
     */
    private String cpf;

    /**
     * Atributo bar, sem significado algum
     */

    private int telefone;
    /**
     * Atributo bar, sem significado algum
     */

    private String email;
    /**
     * Atributo bar, sem significado algum
     */

    private String cidade;
    /**
     * Atributo bar, sem significado algum
     */

    private String estado;
    /**
     * Atributo bar, sem significado algum
     */

    private double pccomissao;
    /**
     * Atributo bar, sem significado algum
     */
    private LocalDate dtcontratacao;

    /**
     * Atributo bar, sem significado algum
     */

    private int indativo;

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    /**
     * Atributo bar, sem significado algum
     */

    private int ddd;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        gerente foo = (gerente) o;
//        return bar == foo.bar && Objects.equals(bas, foo.bas) && Objects.equals(boo, foo.boo);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(bar, bas, boo);
//    }
//
//    @Override
//    public String toString() {
//        return "Foo{" +
//                "bar=" + bar +
//                ", bas='" + bas + '\'' +
//                ", boo='" + boo + '\'' +
//                '}';
//    }
}
