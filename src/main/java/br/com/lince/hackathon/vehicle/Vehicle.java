package br.com.lince.hackathon.vehicle;

import br.com.lince.hackathon.foo.Foo;
import br.com.lince.hackathon.util.Service;

import java.time.LocalDate;
import java.util.Objects;

public class Vehicle {
    private int id;
    private String brand;
    private String model;
    private String plate;
    private String color;
    private String year_of_manufacture;
    private String daily_cost ;
    private double promotion_description;
    private LocalDate type_fuel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYear_of_manufacture() {
        return year_of_manufacture;
    }

    public void setYear_of_manufacture(String year_of_manufacture) {
        this.year_of_manufacture = year_of_manufacture;
    }

    public String getDaily_cost() {
        return daily_cost;
    }

    public void setDaily_cost(String daily_cost) {
        this.daily_cost = daily_cost;
    }

    public double getPromotion_description() {
        return promotion_description;
    }

    public void setPromotion_description(double promotion_description) {
        this.promotion_description = promotion_description;
    }

    public LocalDate getType_fuel() {
        return type_fuel;
    }

    public void setType_fuel(LocalDate type_fuel) {
        this.type_fuel = type_fuel;
    }

    public Vehicle(int id, String brand, String model, String plate, String color, String year_of_manufacture,
                   String daily_cost, double promotion_description, LocalDate type_fuel) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.plate = plate;
        this.color = color;
        this.year_of_manufacture = year_of_manufacture;
        this.daily_cost = daily_cost;
        this.promotion_description = promotion_description;
        this.type_fuel = type_fuel;
    }

    public Vehicle(String brand, String model, String plate, String color, String year_of_manufacture,
                   String daily_cost, double promotion_description, LocalDate type_fuel) {
        this.brand = brand;
        this.model = model;
        this.plate = plate;
        this.color = color;
        this.year_of_manufacture = year_of_manufacture;
        this.daily_cost = daily_cost;
        this.promotion_description = promotion_description;
        this.type_fuel = type_fuel;
    }

    public Vehicle() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id && Objects.equals(this.plate, vehicle.plate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plate);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", plate='" + plate + '\'' +
                ", color='" + color + '\'' +
                ", year_of_manufacture='" + year_of_manufacture + '\'' +
                ", daily_cost='" + daily_cost + '\'' +
                ", promotion_description=" + promotion_description +
                ", type_fuel=" + type_fuel +
                '}';
    }
}
