package com.example.techcorp3.model;

public class CompanyStatistics {
    private Integer employeeCount;
    private Double averageSalary;
    private Double highestSalary;
    private String highestPaidEmployee;

    public CompanyStatistics(Integer employeeCount, Double averageSalary, Double highestSalary,String highestPaidEmployee) {
        this.employeeCount = employeeCount;
        this.averageSalary = averageSalary;
        this.highestSalary = highestSalary;
        this.highestPaidEmployee = highestPaidEmployee;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(Double averageSalary) {
        this.averageSalary = averageSalary;
    }

    public String getHighestPaidEmployee() {
        return highestPaidEmployee;
    }

    public void setHighestPaidEmployee(String highestPaidEmployee) {
        this.highestPaidEmployee = highestPaidEmployee;
    }

    public Double getHighestSalary() {
        return highestSalary;
    }

    public void setHighestSalary(Double highestSalary) {
        this.highestSalary = highestSalary;
    }

    @Override
    public String toString() {
        return "CompanyStatistics{" +
                "employeeCount=" + employeeCount +
                ", averageSalary=" + averageSalary +
                ", highestSalary=" + highestSalary +
                ", highestPaidEmployee='" + highestPaidEmployee + '\'' +
                '}';
    }
}
