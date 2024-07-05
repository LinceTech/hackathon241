package br.com.lince.hackathon.time7;

import java.util.Objects;

public class Veiculo {
    private int    id               ;
    private String marca            ;
    private String modelo           ;
    private String placa            ;
    private String cor              ;
    private int    anoFabrica       ;
    private float  custoDiaria      ;
    private String descricao        ;
    private int    tipoCombustivel  ;

    public Veiculo() {
    }

    public Veiculo(int    id              ,
                   String marca           ,
                   String modelo          ,
                   String placa           ,
                   String cor             ,
                   int    anoFabrica      ,
                   float  custoDiaria     ,
                   String descricao       ,
                   int    tipoCombustivel  ) {

        this.id              = id              ;
        this.marca           = marca           ;
        this.modelo          = modelo          ;
        this.placa           = placa           ;
        this.cor             = cor             ;
        this.anoFabrica      = anoFabrica      ;
        this.custoDiaria     = custoDiaria     ;
        this.descricao       = descricao       ;
        this.tipoCombustivel = tipoCombustivel ;
    }

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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getAnoFabrica() {
        return anoFabrica;
    }

    public void setAnoFabrica(int anoFabrica) {
        this.anoFabrica = anoFabrica;
    }

    public float getCustoDiaria() {
        return custoDiaria;
    }

    public void setCustoDiaria(float custoDiaria) {
        this.custoDiaria = custoDiaria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(int tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Veiculo veiculo = (Veiculo) o;
        return id == veiculo.id && Objects.equals(marca          , veiculo.marca          )
                                && Objects.equals(modelo         , veiculo.modelo         )
                                && Objects.equals(placa          , veiculo.placa          )
                                && Objects.equals(cor            , veiculo.cor            )
                                && Objects.equals(anoFabrica     , veiculo.anoFabrica     )
                                && Objects.equals(custoDiaria    , veiculo.custoDiaria    )
                                && Objects.equals(descricao      , veiculo.descricao      )
                                && Objects.equals(tipoCombustivel, veiculo.tipoCombustivel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, marca , modelo, placa, cor, anoFabrica , custoDiaria, descricao, tipoCombustivel);
    }

    @Override
    public String toString() {
        return "Veiculo {" +
                "  id              = "  + id              +
                ", marca           = '" + marca           + '\'' +
                ", modelo          = '" + modelo          + '\'' +
                ", placa           = '" + placa           + '\'' +
                ", cor             = '" + cor             + '\'' +
                ", anoFabrica      = "  + anoFabrica      +
                ", custoDiaria     = "  + custoDiaria     +
                ", descricao       = '" + descricao       + '\'' +
                ", tipoCombustivel =  " + tipoCombustivel +
                '}';
    }
}
