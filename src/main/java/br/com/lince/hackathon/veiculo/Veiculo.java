package br.com.lince.hackathon.veiculo;

import java.time.LocalDate;

/**
 * A classe Foo é apenas um exemplo de 'Data class', nomes da classe a atributos não tem significado algum,
 * representam apenas uma estrutura para armazenar dados de forma combinada, sem lógica ou inteligencia.
 * <br/>
 * Obs.: Em versões mais recentes do Java, essa classe seria criada como um Record.
 */
public class Veiculo {

    public Veiculo() {
    }

    public Veiculo(int cdveiculo, int indativo, String placa, int cdcor, LocalDate anofabricacao, double valdiario, int indpromocao, String despromocao, int cdcombustivel, double valpromocao, int cdmarca, int cdmodelo) {
        this.cdveiculo = cdveiculo;
        this.placa = placa;
        this.cdcor = cdcor;
        this.anofabricacao = anofabricacao;
        this.valdiario = valdiario;
        this.indpromocao = indpromocao;
        this.despromocao = despromocao;
        this.cdmarca = cdmarca;
        this.cdmodelo = cdmodelo;
        this.indativo = indativo;
        this.cdcombustivel = cdcombustivel;
        this.valpromocao = valpromocao;
    }

    private int cdveiculo;

    private String placa;

    private int cdcor;

    private LocalDate anofabricacao;

    private double valdiario;

    private int indpromocao;

    private String despromocao;

    private int cdcombustivel;

    public double getValpromocao() {
        return valpromocao;
    }

    public void setValpromocao(double valpromocao) {
        this.valpromocao = valpromocao;
    }

    private double valpromocao;

    public int getCdcombustivel() {
        return cdcombustivel;
    }

    public void setCdcombustivel(int cdcombustivel) {
        this.cdcombustivel = cdcombustivel;
    }

    private int cdmarca;

    private int cdmodelo;

    private int indativo;

    public int getIndativo() {
        return indativo;
    }

    public void setIndativo(int indativo) {
        this.indativo = indativo;
    }

    public int getCdveiculo() {
        return cdveiculo;
    }

    public void setCdveiculo(int cdveiculo) {
        this.cdveiculo = cdveiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getCdcor() {
        return cdcor;
    }

    public void setCdcor(int cdcor) {
        this.cdcor = cdcor;
    }

    public LocalDate getAnofabricacao() {
        return anofabricacao;
    }

    public void setAnofabricacao(LocalDate anofabricacao) {
        this.anofabricacao = anofabricacao;
    }

    public double getValdiario() {
        return valdiario;
    }

    public void setValdiario(double valdiario) {
        this.valdiario = valdiario;
    }

    public int getIndpromocao() {
        return indpromocao;
    }

    public void setIndpromocao(int indpromocao) {
        this.indpromocao = indpromocao;
    }

    public String getDespromocao() {
        return despromocao;
    }

    public void setDespromocao(String despromocao) {
        this.despromocao = despromocao;
    }

    public int getCdmarca() {
        return cdmarca;
    }

    public void setCdmarca(int cdmarca) {
        this.cdmarca = cdmarca;
    }

    public int getCdmodelo() {
        return cdmodelo;
    }

    public void setCdmodelo(int cdmodelo) {
        this.cdmodelo = cdmodelo;
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
