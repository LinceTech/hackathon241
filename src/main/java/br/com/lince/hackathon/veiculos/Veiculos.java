package br.com.lince.hackathon.veiculos;

import java.time.LocalDate;

public class Veiculos {
    int id;
    int anoDeFabricacao;
    int tipoDeCombustivel;
    Float custoDeDiaria;
    String cor;
    String placa;
    String modelo;
    String descricaoPromocional;
    String marca;
    boolean semCombustivel= true;
    boolean fgAlcool;
    boolean fgGasolina;
    boolean fgGNV;
    boolean fgEletrico;

    public Veiculos(){
    }

    public Veiculos(String cor, String marca, String placa, String modelo, String descricaoPromocional, int tipoDeCombustivel, int anoDeFabricacao, Float custoDeDiaria) {
        this.cor = cor;
        this.marca = marca;
        this.placa = placa;
        this.modelo = modelo;
        this.descricaoPromocional = descricaoPromocional;
        this.tipoDeCombustivel = tipoDeCombustivel;
        this.anoDeFabricacao = anoDeFabricacao;
        this.custoDeDiaria = custoDeDiaria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAnoDeFabricacao() {
        return anoDeFabricacao == 0 ? "" : Integer.toString(anoDeFabricacao);
    }

    public void setAnoDeFabricacao(int anoDeFabricacao) {this.anoDeFabricacao = anoDeFabricacao;}

    public String getTipoDeCombustivel() {
        return tipoDeCombustivel == 0 ? "" : Integer.toString(tipoDeCombustivel);
    }

    public void setTipoDeCombustivel(int tipoDeCombustivel) {
        this.tipoDeCombustivel = tipoDeCombustivel;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDescricaoPromocional() {
        return descricaoPromocional;
    }

    public void setDescricaoPromocional(String descricaoPromocional) { this.descricaoPromocional = descricaoPromocional; }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCustoDeDiaria() {
        return custoDeDiaria != null ? custoDeDiaria.toString() : "";
    }

    public void setCustoDeDiaria(Float custoDeDiaria) {
        this.custoDeDiaria = custoDeDiaria;
    }

    public boolean isSemCombustivel() {
        return semCombustivel;
    }

    public boolean isFgAlcool() {
        return fgAlcool;
    }

    public boolean isFgGasolina() {
        return fgGasolina;
    }

    public boolean isFgGNV() {
        return fgGNV;
    }

    public boolean isFgEletrico() {
        return fgEletrico;
    }
}
