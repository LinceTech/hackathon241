package br.com.lince.hackathon.util;

public class VehicleType {

    private String type;
    private String name;
    private Boolean selected;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
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

    public VehicleType(String type, String name, Boolean selected) {
        this.type = type;
        this.name = name;
        this.selected = selected;
    }
}
