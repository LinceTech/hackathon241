package br.com.lince.hackathon.veiculos;

import java.util.Objects;

public class Veiculo {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public double getCustoDiaria() {
        return custoDiaria;
    }

    public void setCustoDiaria(double custoDiaria) {
        this.custoDiaria = custoDiaria;
    }

    public String getDescricaoPromocional() {
        return descricaoPromocional;
    }

    public void setDescricaoPromocional(String descricaoPromocional) {
        this.descricaoPromocional = descricaoPromocional;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public Veiculo() {
    }

    public Veiculo(int id, String marca, String modelo, String placa, String cor, int anoFabricacao, double custoDiaria, String descricaoPromocional, String tipoCombustivel) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.cor = cor;
        this.anoFabricacao = anoFabricacao;
        this.custoDiaria = custoDiaria;
        this.descricaoPromocional = descricaoPromocional;
        this.tipoCombustivel = tipoCombustivel;
    }


    @Override
    public String toString() {
        return "Veiculo{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                ", cor='" + cor + '\'' +
                ", anoFabricacao=" + anoFabricacao +
                ", custoDiaria=" + custoDiaria +
                ", descricaoPromocional='" + descricaoPromocional + '\'' +
                ", tipoCombustivel='" + tipoCombustivel + '\'' +
                '}';
    }

    private int id;
    private String marca;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Veiculo veiculo = (Veiculo) o;
        return id == veiculo.id
                && anoFabricacao == veiculo.anoFabricacao
                && Double.compare(custoDiaria, veiculo.custoDiaria) == 0
                && Objects.equals(marca, veiculo.marca)
                && Objects.equals(modelo, veiculo.modelo)
                && Objects.equals(placa, veiculo.placa)
                && Objects.equals(cor, veiculo.cor)
                && Objects.equals(descricaoPromocional, veiculo.descricaoPromocional)
                && Objects.equals(tipoCombustivel, veiculo.tipoCombustivel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, marca, modelo, placa, cor, anoFabricacao, custoDiaria, descricaoPromocional, tipoCombustivel);
    }

    private String modelo;
    private String placa;
    private String cor;
    private int anoFabricacao;
    private double custoDiaria;
    private String descricaoPromocional;
    private String tipoCombustivel;
}
