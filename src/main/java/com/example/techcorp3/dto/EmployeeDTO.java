package com.example.techcorp3.dto;

import com.example.techcorp3.model.Position;

public class EmployeeDTO {
    private String name;
    private String surname;
    private String email;
    private String company;
    private Position position;
    private double salary;

    public EmployeeDTO() {}

    public EmployeeDTO(String name, String surname, String email, String company, Position position, double salary) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.company = company;
        this.position = position;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
