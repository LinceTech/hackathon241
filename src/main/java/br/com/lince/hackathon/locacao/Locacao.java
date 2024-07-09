package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.clientes.Clientes;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Locacao {
    public Long idCliente;
    public Long idGerente;
    public Long idVeiculo;
    public LocalDate dataIncio;
    public LocalDate dataEntrega;
    public BigDecimal valorDiaria;

    public Locacao() {
    }

    public Locacao(
            Long idCliente,
            Long idGerente,
            Long idVeiculo,
            LocalDate dataIncio,
            LocalDate dataEntrega,
            BigDecimal valorDiaria
    ) {
        this.idCliente = idCliente;
        this.idGerente = idGerente;
        this.idVeiculo = idVeiculo;
        this.dataIncio = dataIncio;
        this.dataEntrega = dataEntrega;
        this.valorDiaria = valorDiaria;
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

    public LocalDate getDataEntregadataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public BigDecimal getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(BigDecimal valorDiaria) {
        this.valorDiaria = valorDiaria;
    }
}
