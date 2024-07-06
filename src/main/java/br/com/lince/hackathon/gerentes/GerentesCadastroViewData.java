package br.com.lince.hackathon.gerentes;

import java.util.HashMap;

public class GerentesCadastroViewData {
    private HashMap<String, String> errors;
    private Gerentes gerentes;

    public GerentesCadastroViewData(HashMap<String, String> errors, Gerentes gerentes) {
        this.errors = errors;
        this.gerentes = gerentes;
    }

    public GerentesCadastroViewData() {

    }

    public HashMap<String, String> getErrors() {
        return errors;
    }
    public Gerentes getGerentes() {
        return gerentes;
    }
}
