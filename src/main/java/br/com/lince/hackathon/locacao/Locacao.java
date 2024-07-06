package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.Cliente.Cliente;
import br.com.lince.hackathon.gerente.Gerente;

import java.util.Objects;

public class Locacao {

    private int id;
    private Cliente Cliente;
    private Gerente Gerente;
    //private Veiculo Veiculo;

    private int dataInicio;
    private int dataEntrega;
    private int valorDiaria;
    private int percentualComissao;
    private int valorTotalPago;
    private int dataPagamento;

    public Locacao() {
    }

    public void Cliente () {

    }

    public void Gerente () {
    }

    public Locacao(int id, int dataInicio, int dataEntrega, int valorDiaria, int percentualComissao, int valorTotalPago, int dataPagamento) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataEntrega = dataEntrega;
        this.valorDiaria = valorDiaria;
        this.percentualComissao = percentualComissao;
        this.valorTotalPago = valorTotalPago;
        this.dataPagamento = dataPagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(int valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public int getPercentualComissao() {
        return percentualComissao;
    }

    public void setPercentualComissao(int percentualComissao) {
        this.percentualComissao = percentualComissao;
    }

    public int getValorTotalPago() {
        return valorTotalPago;
    }

    public void setValorTotalPago(int valorTotalPago) {
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
        return id == locacao.id
                && dataInicio         == locacao.dataInicio
                && dataEntrega        == locacao.dataEntrega
                && valorDiaria        == locacao.valorDiaria
                && percentualComissao == locacao.percentualComissao
                && valorTotalPago     == locacao.valorTotalPago
                && dataPagamento      == locacao.dataPagamento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataInicio, dataEntrega, valorDiaria, percentualComissao, valorTotalPago, dataPagamento);
    }

    @Override
    public String toString() {
        return "Locacao{" +
                "id='" + id +
                ", dataInicio='" + dataInicio +
                ", cpf=" + dataEntrega +
                ", dataNascimento=" + valorDiaria +
                ", telefone=" + percentualComissao +
                ", email='" + valorTotalPago +
                ", cep=" + dataPagamento +
                '}';
    }
}
