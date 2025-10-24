package com.example.techcorp3.model;

public class CompanyStatistics {
    private Integer employeeCount;
    private Double averageSalary;
    private String highestPaidEmployee;

    public CompanyStatistics(Integer employeeCount, Double averageSalary, String highestPaidEmployee) {
        this.employeeCount = employeeCount;
        this.averageSalary = averageSalary;
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

    @Override
    public String toString() {
        return "CompanyStatistics{" +
                "employeeCount=" + employeeCount +
                ", averageSalary=" + averageSalary +
                ", highestPaidEmployee='" + highestPaidEmployee + '\'' +
                '}';
    }
}
