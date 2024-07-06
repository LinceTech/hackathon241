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

    public Veiculos(int id, String marca, String modelo, String placa, String cor, int anoDeFabricacao, float custoDeDiaria, String descricaoPromocional, int tipoDeCombustivel) {
        this.id = id;
        this.cor = cor;
        this.marca = marca;
        this.placa = placa;
        this.modelo = modelo;
        this.anoDeFabricacao = anoDeFabricacao;
        this.descricaoPromocional = descricaoPromocional;
        this.tipoDeCombustivel = tipoDeCombustivel;
        this.custoDeDiaria = custoDeDiaria;
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
    public void setAnoDeFabricacao(String anoDeFabricacao) {this.anoDeFabricacao = Integer.parseInt(anoDeFabricacao);}

    public String getTipoDeCombustivel() {
        switch (tipoDeCombustivel) {
            case 1:
                return "alcool";
            case 2:
                return "gasolina";
            case 3:
                return "GNV";
            case 4:
                return "eletrico";
            default:
                return "inválido";
        }
    }

    public void setTipoDeCombustivel(int tipoDeCombustivel) {
        this.tipoDeCombustivel = tipoDeCombustivel;
    }

    public void setTipoDeCombustivel(String tipoDeCombustivel) {
        this.tipoDeCombustivel = Integer.parseInt(tipoDeCombustivel);
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

    public void setCustoDeDiaria(String custoDeDiaria) {
        this.custoDeDiaria = Float.parseFloat(custoDeDiaria);
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
