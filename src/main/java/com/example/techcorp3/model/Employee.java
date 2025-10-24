package com.example.techcorp3.model;

import java.util.Objects;

public class Employee {
    private String name;
    private String surname;
    private String email; // Unique identifier hence no id field
    private String company;
    private Position position;
    private Double salary;

    public Employee(String name, String surname, String email, String company, Position position, Double salary) {
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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // same reference
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(email, employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "Employee{\n" +
                "  name='" + name + '\'' + "\n" +
                "  surname='" + surname + '\'' + "\n" +
                "  email='" + email + '\'' + "\n" +
                "  company='" + company + '\'' + "\n" +
                "  position=" + position + "\n" +
                "  salary=" + salary + "\n" +
                '}';
    }
}
