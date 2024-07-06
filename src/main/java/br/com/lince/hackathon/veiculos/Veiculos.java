package br.com.lince.hackathon.veiculos;

import java.util.HashMap;

public class Veiculos {
    private Long id;
    private String marca;
    private String modelo;
    private String placa;
    private String cor;
    private Double custoDiaria;
    private String descricaoPromocional;
    private String tipoCombustivel;
    private int anoFabricacao;
    private HashMap<String, String> erros;



    public Veiculos() {
    }


    public Veiculos(
            Long id,
            String marca,
            String modelo,
            String placa,
            String cor,
            Double custoDiaria,
            String descricaoPromocional,
            String tipoCombustivel,
            int anoFabricacao
    ) {
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
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {return marca;}
    public void setMarca(String marca) {this.marca = marca;}

    public String getModelo() {return modelo;}
    public void setModelo(String modelo) {this.modelo = modelo;}

    public String getPlaca() {return placa;}
    public void setPlaca(String placa) {this.placa = placa;}

    public String getCor() {return cor;}
    public void setCor(String cor) {this.cor = cor;}

    public int getAnoFabricacao() {return anoFabricacao;}
    public void  setAnoFabricacao(int anoFabricacao) {this.anoFabricacao = anoFabricacao;}

    public Double getCustoDiaria() {return custoDiaria;}
    public void setCustoDiaria(Double custoDiaria) {this.custoDiaria = custoDiaria;}

    public String getDescricaoPromocional() {return descricaoPromocional;}
    public void setDescricaoPromocional(String descricaoPromocional) {this.descricaoPromocional = descricaoPromocional;}

    public String getTipoCombustivel() {return tipoCombustivel;}
    public void setTipoCombustivel(String tipoCombustivel) {this.tipoCombustivel = tipoCombustivel;}

    public HashMap<String, String> getErros() {return erros;}
    public void setErros(HashMap<String, String> erros) { this.erros = erros;}

}