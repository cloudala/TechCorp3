package com.example.techcorp3.model;

public enum Position {
    PREZES(25000.00, 1),
    WICEPREZES(18000.00, 2),
    MANAGER(12000.00, 3),
    PROGRAMISTA(8000.00, 4),
    STAZYSTA(3000.00, 5);

    private final Double baseSalary;
    private final Integer hierarchyLevel;

    Position(Double baseSalary, Integer hierarchyLevel) {
        this.baseSalary = baseSalary;
        this.hierarchyLevel = hierarchyLevel;
    }

    public Integer getHierarchyLevel() {
        return hierarchyLevel;
    }

    public Double getbaseSalary() {
        return baseSalary;
    }
}
