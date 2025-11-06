package com.example.techcorp3.controller;

import com.example.techcorp3.dto.CompanyStatisticsDTO;
import com.example.techcorp3.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final EmployeeService employeeService;

    public StatisticsController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/salary/average")
    public ResponseEntity<Map<String, Double>> getAverageSalary(@RequestParam(required = false) String company) {
        Double average;
        if (company != null && !company.isEmpty()) {
            average = employeeService.calculateAverageSalaryByCompany(company);
        } else {
            average = employeeService.calculateAverageSalary();
        }
        return ResponseEntity.ok(Map.of("averageSalary", average));
    }

    @GetMapping("company/{companyName}")
    public ResponseEntity<CompanyStatisticsDTO> getCompanyStatistics(@PathVariable String companyName) {
        var statsMap = employeeService.getCompanyStatistics();
        if (!statsMap.containsKey(companyName)) {
            return ResponseEntity.notFound().build();
        }
        var stats = statsMap.get(companyName);
        var dto = new CompanyStatisticsDTO(
                companyName,
                stats.getEmployeeCount(),
                stats.getAverageSalary(),
                stats.getHighestSalary(),
                stats.getHighestPaidEmployee()
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/positions")
    public ResponseEntity<Map<String, Long>> getEmployeeCountByPosition() {
        Map<String, Long> positionCounts = employeeService.getEmployeeCountByPosition();
        return ResponseEntity.ok(positionCounts);
    }
}
