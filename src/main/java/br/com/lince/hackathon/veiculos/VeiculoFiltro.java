package br.com.lince.hackathon.veiculos;

public class VeiculoFiltro {
    private String marca;
    private String modelo;
    private String anoDeFabricacao;
    private String cor;
    private String placa;
    private String tipoDeCombustivel;
    boolean semCombustivel= true;
    boolean fgAlcool;
    boolean fgGasolina;
    boolean fgGNV;
    boolean fgEletrico;

    public VeiculoFiltro(String marca, String modelo, String anoDeFabricacao, String cor, String placa, String tipoDeCombustivel) {
        this.marca = marca;
        this.modelo = modelo;
        this.anoDeFabricacao = anoDeFabricacao;
        this.cor = cor;
        this.placa = placa;
        this.tipoDeCombustivel = tipoDeCombustivel;
    }

    public String getMarca() {
        return marca != null ? marca : "";
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo != null ? modelo : "";
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAnoDeFabricacao() {
        return anoDeFabricacao != null ? anoDeFabricacao : "";
    }

    public void setAnoDeFabricacao(String anoDeFabricacao) {
        this.anoDeFabricacao = anoDeFabricacao;
    }

    public String getCor() {
        return cor != null ? cor : "";
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPlaca() {
        return placa != null ? placa : "";
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoDeCombustivel() {
        return tipoDeCombustivel  != null ? tipoDeCombustivel : "";
    }

    public void setTipoDeCombustivel(String tipoDeCombustivel) {
        this.tipoDeCombustivel = tipoDeCombustivel;
    }

    public boolean isSemCombustivel() {
        return semCombustivel;
    }

    public void setSemCombustivel(boolean semCombustivel) {
        this.semCombustivel = semCombustivel;
    }

    public boolean isFgAlcool() {
        return fgAlcool;
    }

    public void setFgAlcool(boolean fgAlcool) {
        this.fgAlcool = fgAlcool;
    }

    public boolean isFgGasolina() {
        return fgGasolina;
    }

    public void setFgGasolina(boolean fgGasolina) {
        this.fgGasolina = fgGasolina;
    }

    public boolean isFgGNV() {
        return fgGNV;
    }

    public void setFgGNV(boolean fgGNV) {
        this.fgGNV = fgGNV;
    }

    public boolean isFgEletrico() {
        return fgEletrico;
    }

    public void setFgEletrico(boolean fgEletrico) {
        this.fgEletrico = fgEletrico;
    }
}
