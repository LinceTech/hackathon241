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
    private int year_of_manufacture;
    private double daily_cost ;
    private String promotion_description;
    private String type_fuel;
    private String type_vehicle;

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

    public int getYear_of_manufacture() {
        return year_of_manufacture;
    }

    public void setYear_of_manufacture(int year_of_manufacture) {
        this.year_of_manufacture = year_of_manufacture;
    }

    public double getDaily_cost() {
        return daily_cost;
    }

    public void setDaily_cost(double daily_cost) {
        this.daily_cost = daily_cost;
    }

    public String getPromotion_description() {
        return promotion_description;
    }

    public void setPromotion_description(String promotion_description) {
        this.promotion_description = promotion_description;
    }

    public String getType_fuel() {
        return type_fuel;
    }

    public void setType_fuel(String type_fuel) {
        this.type_fuel = type_fuel;
    }

    public String getType_vehicle() {
        return type_vehicle;
    }

    public void setType_vehicle(String type_vehicle) {
        this.type_vehicle = type_vehicle;
    }

    public Vehicle(int id, String brand, String model, String plate, String color, int year_of_manufacture,
                   double daily_cost, String promotion_description, String type_fuel, String type_vehicle) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.plate = plate;
        this.color = color;
        this.year_of_manufacture = year_of_manufacture;
        this.daily_cost = daily_cost;
        this.promotion_description = promotion_description;
        this.type_fuel = type_fuel;
        this.type_vehicle = type_vehicle;
    }

    public Vehicle(String brand, String model, String plate, String color, int year_of_manufacture, double daily_cost,
                   String promotion_description, String type_fuel, String type_vehicle) {
        this.brand = brand;
        this.model = model;
        this.plate = plate;
        this.color = color;
        this.year_of_manufacture = year_of_manufacture;
        this.daily_cost = daily_cost;
        this.promotion_description = promotion_description;
        this.type_fuel = type_fuel;
        this.type_vehicle = type_vehicle;
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
