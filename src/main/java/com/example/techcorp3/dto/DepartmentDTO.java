package com.example.techcorp3.dto;

import jakarta.validation.constraints.*;

public class DepartmentDTO {
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String name;

    @NotBlank(message = "Location cannot be empty")
    @Size(max = 50, message = "Location cannot exceed 50 characters")
    private String location;

    @NotNull(message = "Budget is required")
    @Min(value = 0, message = "Budget must be positive")
    private double budget;

    @NotBlank(message = "Manager email is required")
    @Email(message = "Invalid email format")
    private String managerEmail;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public DepartmentDTO(Long id, String name, String location, double budget, String managerEmail) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.budget = budget;
        this.managerEmail = managerEmail;
    }

    public DepartmentDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
