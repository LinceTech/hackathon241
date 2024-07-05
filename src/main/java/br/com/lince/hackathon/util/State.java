package br.com.lince.hackathon.util;

public class State {

    private String uf;
    private String name;

    public State(String uf, String name) {
        this.uf = uf;
        this.name = name;
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
}
