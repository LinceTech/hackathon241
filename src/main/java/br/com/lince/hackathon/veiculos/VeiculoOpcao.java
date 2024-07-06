package br.com.lince.hackathon.veiculos;

public class VeiculoOpcao {
    public Long id;
    public String marca;
    public String modelo;

    public VeiculoOpcao(Long id, String marca, String modelo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
    }

    public VeiculoOpcao() {
    }

    public Long getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
