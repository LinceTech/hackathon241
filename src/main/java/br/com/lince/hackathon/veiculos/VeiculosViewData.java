package br.com.lince.hackathon.veiculos;

import java.util.List;

public class VeiculosViewData {
    private final List<Veiculos> veiculos;
    private final String marca;
    private final String modelo;
    private final int anoFabricacao;
    private final String placa;
    private final String cor;
    private final String tipoCombustivel;
    private final int numeroPagina;

    public VeiculosViewData(
            List<Veiculos> veiculos,
            String marca,
            String modelo,
            int anoFabricacao,
            String placa,
            String cor,
            String tipoCombustivel,
            int numeroPagina
    ) {
        this.veiculos = veiculos;
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.placa = placa;
        this.cor = cor;
        this.tipoCombustivel = tipoCombustivel;
        this.numeroPagina = numeroPagina;
    }

    public List<Veiculos> getVeiculos() {
        return veiculos;
    }

    public String getMarca() {return marca;}

    public String getModelo() {return modelo;}

    public int getAnoFabricacao() {return anoFabricacao;}

    public String getPlaca() {return placa;}

    public String getCor() {return cor;}

    public String getTipoCombustivel() {return tipoCombustivel;}

    public int getNumeroPagina() {
        return numeroPagina;
    }

}
