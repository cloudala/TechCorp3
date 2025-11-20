package com.example.techcorp3.service;

import com.example.techcorp3.exception.DepartmentNotFoundException;
import com.example.techcorp3.model.Department;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {
    private Map<Long, Department> departments = new HashMap<>(Map.of(
            1L, new Department(1L, "HR", "Warsaw HQ", 500_000, "anna.zawacka@techcorp.com"),
            2L, new Department(2L, "IT", "Krakow Office", 1_200_000, "jan.kowalski@techcorp.com"),
            3L, new Department(3L, "Finance", "Warsaw HQ", 800_000, "robert.lewandowski@techcorp.com")
        )
    );

    public List<Department> getAllDepartments() {
        return new ArrayList<>(departments.values());
    }

    public Department getDepartmentById(Long id) {
        Department department = departments.get(id);
        if (department == null) {
            throw new DepartmentNotFoundException("Department with id " + id + " not found");
        }
        return department;
    }

    public Department addDepartment(Department department) {
        Long id = departments.size() + 1L;
        department.setId(id);
        departments.put(id, department);
        return department;
    }

    public Department updateDepartment(Long id, Department updated) {
        if (!departments.containsKey(id)) {
            throw new DepartmentNotFoundException("Department with id " + id + " not found");
        }
        updated.setId(id);
        departments.put(id, updated);
        return updated;
    }


    public void removeDepartmentById(Long id) {
        if (!departments.containsKey(id)) {
            throw new DepartmentNotFoundException("Department with id " + id + " not found");
        }

        departments.remove(id);
    }

}
