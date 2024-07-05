package br.com.lince.hackathon.gerentes;

import java.util.List;

public class GerentesViewData {
    private final List<Gerentes> gerentes;

    public GerentesViewData(List<Gerentes> gerentes) {
        this.gerentes = gerentes;
    }

    public List<Gerentes> getGerentes() {
        return gerentes;
    }
}
