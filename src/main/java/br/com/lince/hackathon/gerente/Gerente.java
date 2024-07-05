package br.com.lince.hackathon.gerente;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDate;
import java.util.Objects;

public class Gerente {

    private String nr_cpf;
    private String nm_gerente;
    private int nr_telefone;
    private String ds_email;
    private String nm_cidade;
    private String nm_estado;
    private double pc_comissao;
    private LocalDate dt_contratacao;
    private LocalDate dt_nascimento;

    public Gerente(){}

    public Gerente(String nr_cpf, String nm_gerente, int nr_telefone, String ds_email, String nm_cidade, String nm_estado, double pc_comissao, LocalDate dt_contratacao, LocalDate dt_nascimento) {
        this.nr_cpf = nr_cpf;
        this.nm_gerente = nm_gerente;
        this.nr_telefone = nr_telefone;
        this.ds_email = ds_email;
        this.nm_cidade = nm_cidade;
        this.nm_estado = nm_estado;
        this.pc_comissao = pc_comissao;
        this.dt_contratacao = dt_contratacao;
        this.dt_nascimento = dt_nascimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gerente gerente = (Gerente) o;
        return Objects.equals(nr_cpf, gerente.nr_cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nr_cpf);
    }

    @Override
    public String toString() {
        return "Gerente{" +
                "nr_cpf='" + nr_cpf + '\'' +
                ", nm_gerente='" + nm_gerente + '\'' +
                ", nr_telefone=" + nr_telefone +
                ", ds_email='" + ds_email + '\'' +
                ", nm_cidade='" + nm_cidade + '\'' +
                ", nm_estado='" + nm_estado + '\'' +
                ", pc_comissao=" + pc_comissao +
                ", dt_contratacao=" + dt_contratacao +
                ", dt_nascimento=" + dt_nascimento +
                '}';
    }

    public void setNr_cpf(String nr_cpf) {
        this.nr_cpf = nr_cpf;
    }

    public void setNm_gerente(String nm_gerente) {
        this.nm_gerente = nm_gerente;
    }

    public void setNr_telefone(int nr_telefone) {
        this.nr_telefone = nr_telefone;
    }

    public void setDs_email(String ds_email) {
        this.ds_email = ds_email;
    }

    public void setNm_cidade(String nm_cidade) {
        this.nm_cidade = nm_cidade;
    }

    public void setNm_estado(String nm_estado) {
        this.nm_estado = nm_estado;
    }

    public void setPc_comissao(double pc_comissao) {
        this.pc_comissao = pc_comissao;
    }

    public void setDt_contratacao(LocalDate dt_contratacao) {
        this.dt_contratacao = dt_contratacao;
    }

    public void setDt_nascimento(LocalDate dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }

    public String getNr_cpf() {
        return nr_cpf;
    }

    public String getNm_gerente() {
        return nm_gerente;
    }

    public int getNr_telefone() {
        return nr_telefone;
    }

    public String getDs_email() {
        return ds_email;
    }

    public String getNm_cidade() {
        return nm_cidade;
    }

    public String getNm_estado() {
        return nm_estado;
    }

    public double getPc_comissao() {
        return pc_comissao;
    }

    public LocalDate getDt_contratacao() {
        return dt_contratacao;
    }

    public LocalDate getDt_nascimento() {
        return dt_nascimento;
    }
}
