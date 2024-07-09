package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.Cliente.Cliente;
import br.com.lince.hackathon.gerente.Gerente;
import br.com.lince.hackathon.veiculo.Veiculo;

import java.util.Objects;

public class Locacao {

    /**
     * Construtor vazio, necessário para uso com JDBI mapper
     */
    public Locacao() {
    }


    public Locacao(int id, long clienteCpf, long gerenteCpf, String placaVeiculo, int dataInicio, int dataEntrega, double valorDiaria, double percentualComissao, double valorTotalPago, int dataPagamento) {
        this.id = id;
        this.clienteCpf = clienteCpf;
        this.gerenteCpf = gerenteCpf;
        this.placaVeiculo = placaVeiculo;
        this.dataInicio = dataInicio;
        this.dataEntrega = dataEntrega;
        this.valorDiaria = valorDiaria;
        this.percentualComissao = percentualComissao;
        this.valorTotalPago = valorTotalPago;
        this.dataPagamento = dataPagamento;
    }

    private int id;
    private long clienteCpf;
    private long gerenteCpf;
    private String placaVeiculo;
    private int dataInicio;
    private int dataEntrega;
    private double valorDiaria;
    private double percentualComissao;
    private double valorTotalPago;
    private int dataPagamento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getClienteCpf() {
        return clienteCpf;
    }

    public void setClienteCpf(long clienteCpf) {
        this.clienteCpf = clienteCpf;
    }

    public long getGerenteCpf() {
        return gerenteCpf;
    }

    public void setGerenteCpf(long gerenteCpf) {
        this.gerenteCpf = gerenteCpf;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public int getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(int dataInicio) {
        this.dataInicio = dataInicio;
    }

    public int getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(int dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public void setPercentualComissao(double percentualComissao) {
        this.percentualComissao = percentualComissao;
    }

    public double getValorTotalPago() {
        return valorTotalPago;
    }

    public void setValorTotalPago(double valorTotalPago) {
        this.valorTotalPago = valorTotalPago;
    }

    public int getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(int dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Locacao locacao = (Locacao) o;
        return id == locacao.id && clienteCpf == locacao.clienteCpf && gerenteCpf == locacao.gerenteCpf && dataInicio == locacao.dataInicio && dataEntrega == locacao.dataEntrega && Double.compare(valorDiaria, locacao.valorDiaria) == 0 && Double.compare(percentualComissao, locacao.percentualComissao) == 0 && Double.compare(valorTotalPago, locacao.valorTotalPago) == 0 && dataPagamento == locacao.dataPagamento && Objects.equals(placaVeiculo, locacao.placaVeiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clienteCpf, gerenteCpf, placaVeiculo, dataInicio, dataEntrega, valorDiaria, percentualComissao, valorTotalPago, dataPagamento);
    }

    @Override
    public String toString() {
        return "Locacao{" +
                "id=" + id +
                ", clienteCpf=" + clienteCpf +
                ", gerenteCpf=" + gerenteCpf +
                ", placaVeiculo='" + placaVeiculo + '\'' +
                ", dataInicio=" + dataInicio +
                ", dataEntrega=" + dataEntrega +
                ", valorDiaria=" + valorDiaria +
                ", percentualComissao=" + percentualComissao +
                ", valorTotalPago=" + valorTotalPago +
                ", dataPagamento=" + dataPagamento +
                '}';
    }
}
