package br.com.lince.hackathon.cliente;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Cliente {
    private String nm_cliente;
    private String nr_cpf;
    private LocalDate dt_nascimento;
    private long nr_telefone;
    private String ds_email;
    private String nm_bairro;
    private String nr_cep;
    private String nm_estado;
    private String nm_cidade;
    private String nm_rua;
    private int nr_residencia;

    public Cliente() {
    }

    /**
     * Construir uma instância de Foo.
     *
     * @param nm_cliente valor para bar
     * @param nr_cpf valor para bas
     * @param nr_telefone valor para boo
     * @param ds_email valor para boo
     * @param nr_cep valor para boo
     * @param nm_cidade valor para boo
     * @param nm_estado valor para bo
     * @param nm_bairro valor para boo
     * @param nm_rua valor para boo
     * @param nr_residencia valor para boo
     */
    public Cliente(String nm_cliente, String nr_cpf, LocalDate dt_nascimento, long nr_telefone, String ds_email, String nm_bairro, String nr_cep, String nm_cidade, String nm_estado, int nr_residencia, String nm_rua) {
        this.nm_cliente = nm_cliente;
        this.nr_cpf = nr_cpf;
        this.dt_nascimento = dt_nascimento;
        this.nr_telefone = nr_telefone;
        this.ds_email = ds_email;
        this.nr_cep = nr_cep;
        this.nm_cidade = nm_cidade;
        this.nm_estado = nm_estado;
        this.nm_bairro = nm_bairro;
        this.nm_rua = nm_rua;
        this.nr_residencia = nr_residencia;
    }

    /**
     * Atributo bar, sem significado algum
     */
    private String bas;


    public String getNm_cliente() {
        return nm_cliente;
    }

    public void setNm_cliente(String nm_cliente) {
        this.nm_cliente = nm_cliente;
    }

    public String getNr_cpf() {
        return nr_cpf;
    }

    public void setNr_cpf(String nr_cpf) {
        this.nr_cpf = nr_cpf;
    }

    public LocalDate getDt_nascimento() {
        return dt_nascimento;
    }

    public void setDt_nascimento(LocalDate dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }

    public long getNr_telefone() {
        return nr_telefone;
    }

    public void setNr_telefone(long nr_telefone) {
        this.nr_telefone = nr_telefone;
    }

    public String getDs_email() {
        return ds_email;
    }

    public void setDs_email(String nm_email) {
        this.ds_email = nm_email;
    }

    public String getNm_bairro() {
        return nm_bairro;
    }

    public void setNm_bairro(String nm_bairro) {
        this.nm_bairro = nm_bairro;
    }

    public String getNr_cep() {
        return nr_cep;
    }

    public void setNr_cep(String nr_cep) {
        this.nr_cep = nr_cep;
    }

    public String getNm_cidade() {
        return nm_cidade;
    }

    public void setNm_cidade(String nm_cidade) {
        this.nm_cidade = nm_cidade;
    }

    public String getNm_estado() {
        return nm_estado;
    }

    public void setNm_estado(String nm_estado) {
        this.nm_estado = nm_estado;
    }

    public int getNr_residencia() {
        return nr_residencia;
    }

    public void setNr_residencia(int nr_residencia) {
        this.nr_residencia = nr_residencia;
    }

    public String getNm_rua() {
        return nm_rua;
    }

    public void setNm_rua(String nm_rua) {
        this.nm_rua = nm_rua;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nr_cpf, cliente.nr_cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nr_cpf);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nm_cliente='" + nm_cliente + '\'' +
                ", nr_cpf='" + nr_cpf + '\'' +
                ", dt_nascimento=" + dt_nascimento +
                ", nr_telefone=" + nr_telefone +
                ", ds_email='" + ds_email + '\'' +
                ", nm_bairro='" + nm_bairro + '\'' +
                ", nr_cep='" + nr_cep + '\'' +
                ", nm_cidade='" + nm_cidade + '\'' +
                ", nm_estado='" + nm_estado + '\'' +
                ", nr_residencia=" + nr_residencia +
                ", nm_rua='" + nm_rua + '\'' +
                '}';
    }
}
