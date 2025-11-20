package com.example.techcorp3.controller;

import com.example.techcorp3.dto.DepartmentDTO;
import com.example.techcorp3.model.Department;
import com.example.techcorp3.service.DepartmentService;
import com.example.techcorp3.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentViewController {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public DepartmentViewController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String getDepartmentsView(Model model) {
        List<Department> departments = departmentService.getAllDepartments();
        List<DepartmentDTO> departmentDTOS = departments.stream().map(dept -> new DepartmentDTO(
                dept.getId(),
                dept.getName(),
                dept.getLocation(),
                dept.getBudget(),
                dept.getManagerEmail()
        )).toList();
        model.addAttribute("departments", departmentDTOS);
        return "departments/list";
    }

    @GetMapping("/{id}")
    public String getDepartmentDetailsView(@PathVariable Long id, Model model) {
        Department department = departmentService.getDepartmentById(id);
        DepartmentDTO departmentDTO = new DepartmentDTO(
                department.getId(),
                department.getName(),
                department.getLocation(),
                department.getBudget(),
                department.getManagerEmail()
        );
        model.addAttribute("department", departmentDTO);
        return "departments/details";
    }

    @GetMapping("/add")
    public String addDepartmentView(Model model) {
        model.addAttribute("managers", employeeService.getPotentialManagers());
        model.addAttribute("department", new DepartmentDTO());
        return "departments/add-form";
    }

    @PostMapping("/add")
    public String addDepartment(@Valid @ModelAttribute("department") DepartmentDTO departmentDTO,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("managers", employeeService.getPotentialManagers());
            return "departments/add-form";
        }

        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setLocation(departmentDTO.getLocation());
        department.setBudget(departmentDTO.getBudget());
        department.setManagerEmail(departmentDTO.getManagerEmail());
        departmentService.addDepartment(department);
        redirectAttributes.addFlashAttribute("message", "Department added successfully");
        return "redirect:/departments";
    }

    @GetMapping("/edit/{id}")
    public String EditDepartmentView(@PathVariable Long id, Model model) {
        Department department = departmentService.getDepartmentById(id);
        DepartmentDTO departmentDTO = new DepartmentDTO(
                department.getId(),
                department.getName(),
                department.getLocation(),
                department.getBudget(),
                department.getManagerEmail()
        );
        model.addAttribute("managers", employeeService.getPotentialManagers());
        model.addAttribute("department", departmentDTO);
        return "departments/edit-form";
    }

    @PostMapping("/edit/{id}")
    public String editDepartment(@PathVariable Long id,
                                 @Valid @ModelAttribute("department") DepartmentDTO departmentDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("managers", employeeService.getPotentialManagers());
            return "departments/edit-form";
        }
        Department department = departmentService.getDepartmentById(id);
        department.setName(departmentDTO.getName());
        department.setLocation(departmentDTO.getLocation());
        department.setBudget(departmentDTO.getBudget());
        department.setManagerEmail(departmentDTO.getManagerEmail());
        departmentService.updateDepartment(id, department);
        redirectAttributes.addFlashAttribute("message", "Department updated successfully");
        return "redirect:/departments";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        departmentService.removeDepartmentById(id);
        redirectAttributes.addFlashAttribute("message", "Department deleted successfully");
        return "redirect:/departments";
    }
}
