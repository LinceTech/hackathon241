package br.com.lince.hackathon.util;

public class State {

    private String uf;
    private String name;
    private Boolean selected;

    public State(String uf, String name, Boolean selected) {
        this.uf = uf;
        this.name = name;
        this.selected = selected;
    }

    public String getUf() {
        return uf;
    }

    public String getName() {
        return name;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
