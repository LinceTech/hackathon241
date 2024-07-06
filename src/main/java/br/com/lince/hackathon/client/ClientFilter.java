package br.com.lince.hackathon.client;

public class ClientFilter {

    private String state;
    private String city;
    private String document;
    private String name;

    public ClientFilter(String state, String city, String document, String name) {
        this.state = state;
        this.city = city;
        this.document = document;
        this.name = name;
    }

    public ClientFilter() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
