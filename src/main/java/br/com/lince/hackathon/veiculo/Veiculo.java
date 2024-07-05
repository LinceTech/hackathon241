package br.com.lince.hackathon.veiculo;

import java.util.Objects;

public class Veiculo {
    public Veiculo() {}

    public Veiculo(int marca, String modelo, String placa, int cor, int anoDeFabricacao, double custoDeDiaria, String descricaoPromocional, int tipoDeCombustivel){
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.cor =  cor;
        this.anoDeFabricacao =  anoDeFabricacao;
        this.custoDeDiaria = custoDeDiaria;
        this.descricaoPromocional = descricaoPromocional;
        this.tipoDeCombustivel = tipoDeCombustivel;
    }

    private int marca;
    private String modelo;
    private String placa;
    private int cor;
    private int anoDeFabricacao;
    private double custoDeDiaria;
    private String descricaoPromocional;
    private int tipoDeCombustivel;

    @Override
    public String toString() {
        return "Veiculo{" +
                "marca=" + marca +
                ", modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                ", cor=" + cor +
                ", anoDeFabricacao=" + anoDeFabricacao +
                ", custoDeDiaria=" + custoDeDiaria +
                ", descricaoPromocional='" + descricaoPromocional + '\'' +
                ", tipoDeCombustivel=" + tipoDeCombustivel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Veiculo veiculo = (Veiculo) o;
        return marca == veiculo.marca && cor == veiculo.cor && anoDeFabricacao == veiculo.anoDeFabricacao && Double.compare(custoDeDiaria, veiculo.custoDeDiaria) == 0 && tipoDeCombustivel == veiculo.tipoDeCombustivel && Objects.equals(modelo, veiculo.modelo) && Objects.equals(placa, veiculo.placa) && Objects.equals(descricaoPromocional, veiculo.descricaoPromocional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca, modelo, placa, cor, anoDeFabricacao, custoDeDiaria, descricaoPromocional, tipoDeCombustivel);
    }

    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }

    public int getTipoDeCombustivel() {
        return tipoDeCombustivel;
    }

    public void setTipoDeCombustivel(int tipoDeCombustivel) {
        this.tipoDeCombustivel = tipoDeCombustivel;
    }

    public String getDescricaoPromocional() {
        return descricaoPromocional;
    }

    public void setDescricaoPromocional(String descricaoPromocional) {
        this.descricaoPromocional = descricaoPromocional;
    }

    public double getCustoDeDiaria() {
        return custoDeDiaria;
    }

    public void setCustoDeDiaria(double custoDeDiaria) {
        this.custoDeDiaria = custoDeDiaria;
    }

    public int getAnoDeFabricacao() {
        return anoDeFabricacao;
    }

    public void setAnoDeFabricacao(int anoDeFabricacao) {
        this.anoDeFabricacao = anoDeFabricacao;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
