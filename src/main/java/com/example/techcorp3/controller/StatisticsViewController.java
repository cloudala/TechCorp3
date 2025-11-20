package com.example.techcorp3.controller;

import com.example.techcorp3.model.CompanyStatistics;
import com.example.techcorp3.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/statistics")
public class StatisticsViewController {

    private final EmployeeService employeeService;

    public StatisticsViewController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String getStatisticsView(Model model) {
        // General stats
        model.addAttribute("totalEmployees", employeeService.getAllEmployees().size());
        model.addAttribute("averageSalary", employeeService.calculateAverageSalary());
        model.addAttribute("totalDepartments", employeeService.getCompanyStatistics().size());

        // Per-company stats
        Map<String, CompanyStatistics> companyStats = employeeService.getCompanyStatistics();
        model.addAttribute("companyStats", companyStats);

        // Employees per position
        Map<String, Long> employeesByPosition = employeeService.getEmployeeCountByPosition();
        model.addAttribute("employeesByPosition", employeesByPosition);

        return "statistics/index";
    }
}
