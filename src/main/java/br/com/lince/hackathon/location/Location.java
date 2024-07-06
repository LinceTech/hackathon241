package br.com.lince.hackathon.location;

import java.time.LocalDate;

public class Location {
    private Integer id;
    private Integer id_client;
    private Integer id_maneger;
    private Integer id_vehicles;
    private LocalDate date_start;
    private LocalDate date_delivery;
    private Double day_value;
    private Double commission_percentage;
    private Double total_value;
    private LocalDate date_pay;

    public Location(Integer id, Integer id_client, Integer id_maneger, Integer id_vehicles, LocalDate date_start,
                    LocalDate date_delivery, Double day_value, Double commission_percentage, Double total_value, LocalDate date_pay) {
        this.id = id;
        this.id_client = id_client;
        this.id_maneger = id_maneger;
        this.id_vehicles = id_vehicles;
        this.date_start = date_start;
        this.date_delivery = date_delivery;
        this.day_value = day_value;
        this.commission_percentage = commission_percentage;
        this.total_value = total_value;
        this.date_pay = date_pay;
    }

    public Location() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_client() {
        return id_client;
    }

    public void setId_client(Integer id_client) {
        this.id_client = id_client;
    }

    public Integer getId_maneger() {
        return id_maneger;
    }

    public void setId_maneger(Integer id_maneger) {
        this.id_maneger = id_maneger;
    }

    public Integer getId_vehicles() {
        return id_vehicles;
    }

    public void setId_vehicles(Integer id_vehicles) {
        this.id_vehicles = id_vehicles;
    }

    public LocalDate getDate_start() {
        return date_start;
    }

    public void setDate_start(LocalDate date_start) {
        this.date_start = date_start;
    }

    public LocalDate getDate_delivery() {
        return date_delivery;
    }

    public void setDate_delivery(LocalDate date_delivery) {
        this.date_delivery = date_delivery;
    }

    public Double getDay_value() {
        return day_value;
    }

    public void setDay_value(Double day_value) {
        this.day_value = day_value;
    }

    public Double getCommission_percentage() {
        return commission_percentage;
    }

    public void setCommission_percentage(Double commission_percentage) {
        this.commission_percentage = commission_percentage;
    }

    public Double getTotal_value() {
        return total_value;
    }

    public void setTotal_value(Double total_value) {
        this.total_value = total_value;
    }

    public LocalDate getDate_pay() {
        return date_pay;
    }

    public void setDate_pay(LocalDate date_pay) {
        this.date_pay = date_pay;
    }
}
