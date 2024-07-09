package br.com.lince.hackathon.locacao;

import java.sql.Date;

public class Locacao {
    private int id;
    private int cliente;
    private int gerenteResponsavel;
    private int veiculo;
    private Date dataInicio;
    private Date dataEntregaVeiculo;
    private float valorDiaria;
    private float percentualComissaoGerente;
    private float valorTotalPago;
    private Date dataPagamento;

    public Locacao() {
    }

    public Locacao(int id, int cliente, int gerenteResponsavel, int veiculo, Date dataInicio, Date dataEntregaVeiculo, float valorDiaria, float percentualComissaoGerente, float valorTotalPago, Date dataPagamento) {
        this.id = id;
        this.cliente = cliente;
        this.gerenteResponsavel = gerenteResponsavel;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataEntregaVeiculo = dataEntregaVeiculo;
        this.valorDiaria = valorDiaria;
        this.percentualComissaoGerente = percentualComissaoGerente;
        this.valorTotalPago = valorTotalPago;
        this.dataPagamento = dataPagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(int veiculo) {
        this.veiculo = veiculo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public float getValorTotalPago() {
        return valorTotalPago;
    }

    public void setValorTotalPago(float valorTotalPago) {
        this.valorTotalPago = valorTotalPago;
    }

    public float getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(float valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public Date getDataEntregaVeiculo() {
        return dataEntregaVeiculo;
    }

    public void setDataEntregaVeiculo(Date dataEntregaVeiculo) {
        this.dataEntregaVeiculo = dataEntregaVeiculo;
    }

    public int getGerenteResponsavel() {
        return gerenteResponsavel;
    }

    public void setGerenteResponsavel(int gerenteResponsavel) {
        this.gerenteResponsavel = gerenteResponsavel;
    }

    public float getPercentualComissaoGerente() {
        return percentualComissaoGerente;
    }

    public void setPercentualComissaoGerente(float percentualComissaoGerente) {
        this.percentualComissaoGerente = percentualComissaoGerente;
    }
}
