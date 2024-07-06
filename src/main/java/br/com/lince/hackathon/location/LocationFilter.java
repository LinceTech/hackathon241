package br.com.lince.hackathon.location;

public class LocationFilter {

    private String client;
    private String manager;
    private String vehicles;
    private String initialDate;
    private String finalDate;

    public LocationFilter(String client, String manager, String vehicles, String initialDate, String finalDate) {
        this.client = client;
        this.manager = manager;
        this.vehicles = vehicles;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public LocationFilter() {
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getVehicles() {
        return vehicles;
    }

    public void setVehicle(String vehicles) {
        this.vehicles = vehicles;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getFinalDate() {
        return initialDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }
}
