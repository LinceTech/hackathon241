package br.com.lince.hackathon.manager;

import br.com.lince.hackathon.util.Service;

import java.time.LocalDate;

public class Manager {
    private int id;
    private String cpf;
    private String name;
    private String email;
    private String phone;
    private String city;
    private String state;
    private double commission_percentage;
    private LocalDate hiring_date;
    private LocalDate date_birth;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
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

    public LocalDate getHiring_date() {
        return hiring_date;
    }

    public void setHiring_date(LocalDate hiring_date) {
        this.hiring_date = hiring_date;
    }

    public LocalDate getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(LocalDate date_birth) {
        this.date_birth = date_birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Manager(int id, String cpf, String name, String email, String phone, String city,
                   String state, double commission_percentage, LocalDate hiring_date, LocalDate date_birth) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.state = state;
        this.commission_percentage = commission_percentage;
        this.hiring_date = hiring_date;
        this.date_birth = date_birth;
    }

    public Manager(String cpf, String name, String email, String phone, String city,
                   String state, double commission_percentage, LocalDate hiring_date, LocalDate date_birth) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.state = state;
        this.commission_percentage = commission_percentage;
        this.hiring_date = hiring_date;
        this.date_birth = date_birth;
    }

    public Manager() {
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", cpf=" + cpf +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", commission_percentage=" + commission_percentage +
                ", hiring_date=" + hiring_date +
                ", date_birth=" + date_birth +
                '}';
    }


}
