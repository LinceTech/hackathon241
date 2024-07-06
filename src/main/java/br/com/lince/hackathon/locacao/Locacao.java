package br.com.lince.hackathon.locacao;

import java.time.LocalDate;

/**
 * A classe Foo é apenas um exemplo de 'Data class', nomes da classe a atributos não tem significado algum,
 * representam apenas uma estrutura para armazenar dados de forma combinada, sem lógica ou inteligencia.
 * <br/>
 * Obs.: Em versões mais recentes do Java, essa classe seria criada como um Record.
 */
public class Locacao {

    public Locacao() {
    }

    public Locacao(int cdlocacao, int cdcliente, int cdgerente, int cdveiculo, LocalDate dtinicio, LocalDate dtentrega, double valdiaria, double pccomissao, double valpago , LocalDate datapagamento ) {
        this.cdlocacao = cdlocacao;
        this.cdcliente = cdcliente;
        this.cdgerente = cdgerente;
        this.cdveiculo = cdveiculo;
        this.dtinicio = dtinicio;
        this.dtentrega = dtentrega;
        this.valdiaria = valdiaria;
        this.pccomissao = pccomissao;
        this.valpago = valpago;
        this.datapagamento = datapagamento;
    }

    private int cdlocacao;

    private int cdcliente;

    private int cdgerente;

    private int cdveiculo;

    private LocalDate dtinicio ;

    private LocalDate dtentrega ;

    private double valdiaria;

    private double pccomissao;

    private double valpago;

    private LocalDate datapagamento;

    public int getCdlocacao() {
        return cdlocacao;
    }

    public void setCdlocacao(int cdlocacao) {
        this.cdlocacao = cdlocacao;
    }

    public int getCdcliente() {
        return cdcliente;
    }

    public void setCdcliente(int cdcliente) {
        this.cdcliente = cdcliente;
    }

    public int getCdgerente() {
        return cdgerente;
    }

    public void setCdgerente(int cdgerente) {
        this.cdgerente = cdgerente;
    }

    public int getCdveiculo() {
        return cdveiculo;
    }

    public void setCdveiculo(int cdveiculo) {
        this.cdveiculo = cdveiculo;
    }

    public LocalDate getDtinicio() {
        return dtinicio;
    }

    public void setDtinicio(LocalDate dtinicio) {
        this.dtinicio = dtinicio;
    }

    public LocalDate getDtentrega() {
        return dtentrega;
    }

    public void setDtentrega(LocalDate dtentrega) {
        this.dtentrega = dtentrega;
    }

    public double getValdiaria() {
        return valdiaria;
    }

    public void setValdiaria(double valdiaria) {
        this.valdiaria = valdiaria;
    }

    public double getPccomissao() {
        return pccomissao;
    }

    public void setPccomissao(double pccomissao) {
        this.pccomissao = pccomissao;
    }

    public double getValpago() {
        return valpago;
    }

    public void setValpago(double valpago) {
        this.valpago = valpago;
    }

    public LocalDate getDatapagamento() {
        return datapagamento;
    }

    public void setDatapagamento(LocalDate datapagamento) {
        this.datapagamento = datapagamento;
    }

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
