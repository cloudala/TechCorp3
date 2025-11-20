package com.example.techcorp3.controller;

import com.example.techcorp3.dto.EmployeeDTO;
import com.example.techcorp3.model.Employee;
import com.example.techcorp3.model.EmploymentStatus;
import com.example.techcorp3.model.Position;
import com.example.techcorp3.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeViewController {
    private final EmployeeService employeeService;

    public EmployeeViewController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String getEmployeesView(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee emp : employees) {
            EmployeeDTO dto = new EmployeeDTO(
                    emp.getName(),
                    emp.getSurname(),
                    emp.getEmail(),
                    emp.getCompany(),
                    emp.getPosition(),
                    emp.getSalary()
            );
            employeeDTOS.add(dto);
        }
        model.addAttribute("employees", employeeDTOS);
        return "employees/list";
    }

    @GetMapping("/add")
    public String addEmployeeView(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        model.addAttribute("positions", Position.values());
        model.addAttribute("employmentStatuses", EmploymentStatus.values());
        return "employees/add-form";
    }

    @PostMapping("/add")
    public String addEmployee(@Valid @ModelAttribute("employee") EmployeeDTO employeeDTO,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("positions", Position.values());
            model.addAttribute("employmentStatuses", EmploymentStatus.values());
            return "employees/add-form";
        }

        Employee employee = new Employee(
                employeeDTO.getName(),
                employeeDTO.getSurname(),
                employeeDTO.getEmail(),
                employeeDTO.getCompany(),
                employeeDTO.getPosition(),
                employeeDTO.getSalary()
        );
        employeeService.addEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "Pracownik dodany pomyślnie");
        return "redirect:/employees";
    }

    @GetMapping("/edit/{email}")
    public String editEmployeeView(@PathVariable String email, Model model) {
        Employee employee = employeeService.findEmployeeByEmail(email);
        if (employee == null) {
            return "redirect:/employees";
        }
        EmployeeDTO dto = new EmployeeDTO(
                employee.getName(),
                employee.getSurname(),
                employee.getEmail(),
                employee.getCompany(),
                employee.getPosition(),
                employee.getSalary()
        );
        model.addAttribute("employee", dto);
        model.addAttribute("positions", Position.values());
        model.addAttribute("employmentStatuses", EmploymentStatus.values());
        return "employees/edit-form";
    }

    @PostMapping("/edit")
    public String editEmployee(@Valid @ModelAttribute("employee") EmployeeDTO employeeDTO,
                               BindingResult bindingResult,
                               Model model,
                               @RequestParam("oldEmail") String oldEmail,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("positions", Position.values());
            model.addAttribute("employmentStatuses", EmploymentStatus.values());
            return "employees/edit-form";
        }

        Employee updated = new Employee(
                employeeDTO.getName(),
                employeeDTO.getSurname(),
                employeeDTO.getEmail(),
                employeeDTO.getCompany(),
                employeeDTO.getPosition(),
                employeeDTO.getSalary()
        );
        employeeService.updateEmployee(oldEmail, updated);
        redirectAttributes.addFlashAttribute("message", "Dane pracownika zaktualizowane pomyślnie");
        return "redirect:/employees";
    }

    @GetMapping("/delete/{email}")
    public String deleteEmployee(@PathVariable String email, RedirectAttributes redirectAttributes) {
        employeeService.removeEmployeeByEmail(email);
        redirectAttributes.addFlashAttribute("message", "Pracownik usunięty pomyślnie");
        return "redirect:/employees";
    }

    @GetMapping("/search")
    public String searchEmployeesFromCompanyView() {
        return "employees/search-form";
    }

    @PostMapping("/search")
    public String searchEmployeesFromCompany(@RequestParam("company") String company, Model model) {
        List<Employee> employees = employeeService.getEmployeesByCompany(company);
        List<EmployeeDTO> employeeDTOS = employees.stream().map(emp ->
                new EmployeeDTO(
                        emp.getName(),
                        emp.getSurname(),
                        emp.getEmail(),
                        emp.getCompany(),
                        emp.getPosition(),
                        emp.getSalary()
                )).toList();
        model.addAttribute("company", company);
        model.addAttribute("employees", employeeDTOS);
        return "employees/search-results";
    }
}
