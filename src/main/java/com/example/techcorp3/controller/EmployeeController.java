package com.example.techcorp3.controller;

import com.example.techcorp3.dto.EmployeeDTO;
import com.example.techcorp3.model.Employee;
import com.example.techcorp3.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@RequestParam(required = false) String company) {
        List<Employee> employees;
        if (company == null || company.isEmpty()) {
            employees = employeeService.getAllEmployees();
        } else {
            employees = employeeService.getEmployeesByCompany(company);
        }
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(employee ->
                        new EmployeeDTO(
                                employee.getName(),
                                employee.getSurname(),
                                employee.getEmail(),
                                employee.getCompany(),
                                employee.getPosition(),
                                employee.getSalary()
                        ))
                .toList();
        return ResponseEntity.ok(employeeDTOs);
    }

    @GetMapping("/{email}")
    public ResponseEntity<EmployeeDTO> getEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeService.findEmployeeByEmail(email);
        EmployeeDTO employeeDTO = new EmployeeDTO(
                employee.getName(),
                employee.getSurname(),
                employee.getEmail(),
                employee.getCompany(),
                employee.getPosition(),
                employee.getSalary()
        );
        return ResponseEntity.ok(employeeDTO);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.addEmployee(new Employee(
                employeeDTO.getName(),
                employeeDTO.getSurname(),
                employeeDTO.getEmail(),
                employeeDTO.getCompany(),
                employeeDTO.getPosition(),
                employeeDTO.getSalary()
        ));
        return ResponseEntity.created(URI.create("/api/employees")).body(employeeDTO);
    }

    @PutMapping("/{email}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable String email, @RequestBody EmployeeDTO employeeDTO) {
        Employee updated = new Employee(
                employeeDTO.getName(),
                employeeDTO.getSurname(),
                employeeDTO.getEmail(),
                employeeDTO.getCompany(),
                employeeDTO.getPosition(),
                employeeDTO.getSalary()
        );
        employeeService.updateEmployee(email, updated);
        return ResponseEntity.ok(employeeDTO);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String email) {
        employeeService.findEmployeeByEmail(email);
        employeeService.removeEmployeeByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
