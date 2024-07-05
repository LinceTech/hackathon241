package br.com.lince.hackathon.locacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Locacao {
    public Long idCliente;
    public Long idGerente;
    public Long idVeiculo;
    public LocalDate dataIncio;
    public LocalDate dataEntraga;
    public BigDecimal valorDiaria;
    public double percentualGerente;
    public BigDecimal valorTotal;
    public LocalDate dataPagamento;
    public int devolvido;

    public Locacao() {
    }

    public Locacao(
            Long idCliente,
            Long idGerente,
            Long idVeiculo,
            LocalDate dataIncio,
            LocalDate dataEntraga,
            BigDecimal valorDiaria,
            double percentualGerente,
            BigDecimal valorTotal,
            LocalDate dataPagamento,
            int devolvido
    ) {
        this.idCliente = idCliente;
        this.idGerente = idGerente;
        this.idVeiculo = idVeiculo;
        this.dataIncio = dataIncio;
        this.dataEntraga = dataEntraga;
        this.valorDiaria = valorDiaria;
        this.percentualGerente = percentualGerente;
        this.valorTotal = valorTotal;
        this.dataPagamento = dataPagamento;
        this.devolvido = devolvido;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdGerente() {
        return idGerente;
    }

    public void setIdGerente(Long idGerente) {
        this.idGerente = idGerente;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public LocalDate getDataIncio() {
        return dataIncio;
    }

    public void setDataIncio(LocalDate dataIncio) {
        this.dataIncio = dataIncio;
    }

    public LocalDate getDataEntraga() {
        return dataEntraga;
    }

    public void setDataEntraga(LocalDate dataEntraga) {
        this.dataEntraga = dataEntraga;
    }

    public BigDecimal getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(BigDecimal valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public double getPercentualGerente() {
        return percentualGerente;
    }

    public void setPercentualGerente(double percentualGerente) {
        this.percentualGerente = percentualGerente;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public int isDevolvido() {
        return devolvido;
    }

    public void setDevolvido(int devolvido) {
        this.devolvido = devolvido;
    }
}
