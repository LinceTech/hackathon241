package br.com.lince.hackathon.veiculos;

import java.util.HashMap;

public class VeiculosCadastroViewData {
    private HashMap<String, String> errors;
    private Veiculos veiculos;

    public VeiculosCadastroViewData(HashMap<String, String> errors, Veiculos veiculos) {
        this.errors = errors;
        this.veiculos = veiculos;
    }

    public VeiculosCadastroViewData() {}

    public HashMap<String, String> getErrors() {
        return errors;
    }
    public Veiculos getVeiculos() {
        return veiculos;
    }
}
