package br.com.lince.hackathon.manager;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class manager {
    private int id;
    private int cpf;
    private String name;
    private String email;
    private String city;
    private String state;
    private double commission_percentage;
    private Date hiring_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getCommission_percentage() {
        return commission_percentage;
    }

    public void setCommission_percentage(double commission_percentage) {
        this.commission_percentage = commission_percentage;
    }

    public Date getHiring_date() {
        return hiring_date;
    }

    public void setHiring_date(Date hiring_date) {
        this.hiring_date = hiring_date;
    }

    public manager(int cpf, String name, String email, String city, String state, double commission_percentage, Date hiring_date) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.city = city;
        this.state = state;
        this.commission_percentage = commission_percentage;
        this.hiring_date = hiring_date;
    }

    public manager() {
    }

    @Override
    public String toString() {
        return "manager{" +
                "id=" + id +
                ", cpf=" + cpf +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", commission_percentage=" + commission_percentage +
                ", hiring_date=" + hiring_date +
                '}';
    }


}
