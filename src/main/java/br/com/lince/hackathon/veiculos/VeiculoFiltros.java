package br.com.lince.hackathon.veiculos;

import java.util.HashMap;
import java.util.List;

public class VeiculoFiltros {
    private final String marca;
    private final String modelo;
    private final int anoFabricacao;
    private final String placa;
    private final String cor;
    private final String tipoCombustivel;

    public VeiculoFiltros(String marca, String modelo, int anoFabricacao, String placa, String cor, String tipoCombustivel) {
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.placa = placa;
        this.cor = cor;
        this.tipoCombustivel = tipoCombustivel;
    }

    public String getMarca() {return marca;}

    public String getModelo() {return modelo;}

    public int getAnoFabricacao() {return anoFabricacao;}

    public String getPlaca() {return placa;}

    public String getCor() {return cor;}

    public String getTipoCombustivel() {return tipoCombustivel;}

}
