package com.example.techcorp3.service;

import com.example.techcorp3.exception.DuplicateEmailException;
import com.example.techcorp3.exception.EmployeeNotFoundException;
import com.example.techcorp3.model.CompanyStatistics;
import com.example.techcorp3.model.Employee;
import com.example.techcorp3.model.Position;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    // In-memory storage for employees
    // Empty list
    // private List<Employee> employees = new ArrayList<>();

    // Pre-populated list
    private final List<Employee> employees = new ArrayList<>(Arrays.asList(
            new Employee("Jan", "Kowalski", "jan.kowalski@techcorp.com", "TechCorp", Position.MANAGER, 12000.0),
            new Employee("Anna", "Zawacka", "anna.zawacka@techcorp.com", "TechCorp", Position.PROGRAMISTA, 8500.0),
            new Employee("Joanna", "Nowak", "joanna.nowak@techcorp.com", "TechCorp", Position.PROGRAMISTA, 9000.0),
            new Employee("Robert", "Lewandowski", "robert.lewandowski@techcorp.com", "TechCorp", Position.PREZES, 25000.0),
            new Employee("Maria", "Wiśniewska", "maria.wisniewska@techcorp.com", "TechCorp", Position.MANAGER, 12000.0),
            new Employee("Piotr", "Kaczmarek", "piotr.kaczmarek@othercorp.com", "OtherCorp", Position.PROGRAMISTA, 8000.0),
            new Employee("Katarzyna", "Sikorska", "katarzyna.sikorska@techcorp.com", "TechCorp", Position.STAZYSTA, 3000.0),
            new Employee("Tomasz", "Grabowski", "tomasz.grabowski@othercorp.com", "OtherCorp", Position.WICEPREZES, 18000.0),
            new Employee("Ewa", "Nowicka", "ewa.nowicka@techcorp.com", "TechCorp", Position.PROGRAMISTA, 850.0),
            new Employee("Michał", "Wojcik", "michal.wojcik@othercorp.com", "OtherCorp", Position.MANAGER, 12000.0),
            new Employee("Paweł", "Lewicki", "pawel.lewicki@othercorp.com", "OtherCorp", Position.PROGRAMISTA, 8500.0),
            new Employee("Karolina", "Sadowska", "karolina.sadowska@techcorp.com", "TechCorp", Position.STAZYSTA, 300.0),
            new Employee("Mateusz", "Kowalczyk", "mateusz.kowalczyk@othercorp.com", "OtherCorp", Position.PROGRAMISTA, 8000.0),
            new Employee("Zofia", "Jankowska", "zofia.jankowska@techcorp.com", "TechCorp", Position.MANAGER, 12000.0),
            new Employee("Łukasz", "Mazur", "lukasz.mazur@othercorp.com", "OtherCorp", Position.WICEPREZES, 1800.0),
            new Employee("Natalia", "Pawlak", "natalia.pawlak@techcorp.com", "TechCorp", Position.PROGRAMISTA, 8500.0),
            new Employee("Krzysztof", "Wróblewski", "krzysztof.wroblewski@othercorp.com", "OtherCorp", Position.STAZYSTA, 3000.0),
            new Employee("Monika", "Kwiatkowska", "monika.kwiatkowska@techcorp.com", "TechCorp", Position.PROGRAMISTA, 9000.0)
    ));

    public EmployeeService() {
        System.out.println("Employee service initialized");
    }


    public List<Employee> getAllEmployees() {
        return employees;
    }

    public Employee findEmployeeByEmail(String email) {
        return employees.stream()
                .filter(employee -> employee.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with email " + email + " not found"));
    }

    public void removeEmployeeByEmail(String email) {
        boolean removed = employees.removeIf(employee -> employee.getEmail().equalsIgnoreCase(email));
        if (!removed) {
            throw new EmployeeNotFoundException("Employee with email " + email + " not found");
        }
    }

    public void clearEmployees() {
        employees.clear();
    }

    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        boolean exists = employees.stream()
                .anyMatch(emp -> emp.getEmail().equalsIgnoreCase(employee.getEmail()));

        if (exists) {
            throw new DuplicateEmailException("Employee with email " + employee.getEmail() + " already exists");
        }

        employees.add(employee);
    }

    public List<Employee> getEmployeesByCompany(String company) {
        return employees.stream()
                .filter(employee -> employee.getCompany().equalsIgnoreCase(company))
                .collect(Collectors.toList());
    }

    public List<Employee> sortEmployeesBySurname() {
        return employees.stream()
                .sorted(Comparator.comparing(employee -> employee.getSurname()))
                .collect(Collectors.toList());
    }

    public Map<Position, List<Employee>> groupEmployeesByPosition() {
        return employees.stream()
                .collect(Collectors.groupingBy(employee -> employee.getPosition()));
    }

    public Map<Position, Integer> countEmployeesByPosition() {
        return employees.stream()
                .collect(Collectors.groupingBy(employee -> employee.getPosition(),
                        Collectors.summingInt(e -> 1)));
    }

    public Double calculateAverageSalary() {
        return employees.stream()
                .mapToDouble(employee -> employee.getSalary())
                .average()
                .orElse(0.0);
    }

    public Double calculateAverageSalaryByCompany(String company) {
        return employees.stream()
                .filter(employee -> employee.getCompany().equalsIgnoreCase(company))
                .mapToDouble(employee -> employee.getSalary())
                .average()
                .orElse(0.0);
    }

    public Optional<Employee> getEmployeeWithHighestSalary() {
        return employees.stream()
                .max(Comparator.comparing(employee -> employee.getSalary()));
    }

    public List<Employee> validateSalaryConsistency() {
        return employees.stream()
                .filter(employee -> employee.getSalary() < employee.getPosition().getbaseSalary())
                .collect(Collectors.toList());
    }

    public Map<String, CompanyStatistics> getCompanyStatistics() {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getCompany,
                        Collectors.collectingAndThen(Collectors.toList(), list -> {
                            int count = list.size();
                            double avgSalary = list.stream()
                                    .mapToDouble(Employee::getSalary)
                                    .average()
                                    .orElse(0.0);
                            double highestSalary = list.stream()
                                    .mapToDouble(Employee::getSalary)
                                    .max()
                                    .orElse(0.0);
                            String highestPaid = list.stream()
                                    .max(Comparator.comparingDouble(Employee::getSalary))
                                    .map(emp -> emp.getName() + " " + emp.getSurname())
                                    .orElse("N/A");
                            return new CompanyStatistics(count, avgSalary, highestSalary, highestPaid);
                        })));
    }

    public Map<String, Long> getEmployeeCountByPosition() {
        return employees.stream()
                .collect(Collectors.groupingBy(
                        employee -> employee.getPosition().name(),
                        Collectors.counting()
                ));
    }
}
