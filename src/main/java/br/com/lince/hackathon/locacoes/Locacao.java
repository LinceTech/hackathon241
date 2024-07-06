package br.com.lince.hackathon.locacoes;

import br.com.lince.hackathon.clientes.Cliente;
import br.com.lince.hackathon.gerentes.Gerentes;
import br.com.lince.hackathon.veiculos.Veiculos;

import java.util.Date;

public class Locacao {
    private int id;
    private Cliente id_cliente;
    private Gerentes id_gerente;
    private Veiculos id_veiculo;
    private Date data_inicio;
    private Date data_entrega;
    private double valor_diaria;
    private double percentual_diaria;
    private double percentual_comissao;
    private double valor_total_pago;
    private Date data_pagamento;

    public Locacao() {}

    public Locacao(int id, Cliente id_cliente, Gerentes id_gerente, Veiculos id_veiculo, Date data_inicio, Date data_entrega, double valor_diaria, double percentual_diaria, double percentual_comissao, double valor_total_pago, Date data_pagamento) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_gerente = id_gerente;
        this.id_veiculo = id_veiculo;
        this.data_inicio = data_inicio;
        this.data_entrega = data_entrega;
        this.valor_diaria = valor_diaria;
        this.percentual_diaria = percentual_diaria;
        this.percentual_comissao = percentual_comissao;
        this.valor_total_pago = valor_total_pago;
        this.data_pagamento = data_pagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Cliente id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Gerentes getId_gerente() {
        return id_gerente;
    }

    public void setId_gerente(Gerentes id_gerente) {
        this.id_gerente = id_gerente;
    }

    public Veiculos getId_veiculo() {
        return id_veiculo;
    }

    public void setId_veiculo(Veiculos id_veiculo) {
        this.id_veiculo = id_veiculo;
    }

    public Date getData_inicio() {
        return data_inicio != null ? data_inicio : new Date();
    }

    public void setData_inicio(Date data_inicio) {
        this.data_inicio = data_inicio;
    }

    public Date getData_entrega() {
        return data_entrega != null ? data_entrega : new Date();
    }

    public void setData_entrega(Date data_entrega) {
        this.data_entrega = data_entrega;
    }

    public double getValor_diaria() {
        return valor_diaria;
    }

    public void setValor_diaria(double valor_diaria) {
        this.valor_diaria = valor_diaria;
    }

    public double getPercentual_diaria() {
        return percentual_diaria;
    }

    public void setPercentual_diaria(double percentual_diaria) {
        this.percentual_diaria = percentual_diaria;
    }

    public double getPercentual_comissao() {
        return percentual_comissao;
    }

    public void setPercentual_comissao(double percentual_comissao) {
        this.percentual_comissao = percentual_comissao;
    }

    public double getValor_total_pago() {
        return valor_total_pago;
    }

    public void setValor_total_pago(double valor_total_pago) {
        this.valor_total_pago = valor_total_pago;
    }

    public Date getData_pagamento() {
        return data_pagamento != null ? data_pagamento : new Date();
    }

    public void setData_pagamento(Date data_pagamento) {
        this.data_pagamento = data_pagamento;
    }
}
