package com.example.techcorp3.service;

import com.example.techcorp3.model.Employee;
import com.example.techcorp3.model.ImportSummary;
import com.example.techcorp3.model.Position;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportService {
    private final EmployeeService employeeService;
    private final String filePath;

    public ImportService(EmployeeService employeeService, @Value("${app.import.csv-file}") String filePath) {
        System.out.println("ImportService initialized with EmployeeService dependency and csv-file path from application.properties");
        this.employeeService = employeeService;
        this.filePath = filePath;
    }

    public ImportSummary importFromCsv() {
        List<String> errors = new ArrayList<>();
        int importedCount = 0;

        try (Reader r = getReaderForPath(filePath);
             CSVReader reader = new CSVReader(r)) {

            List<String[]> allRows = reader.readAll();

            for (int i = 1; i < allRows.size(); i++) {
                String[] row = allRows.get(i);
                int lineNumber = i + 1;

                if (row == null || row.length == 0) continue;

                boolean allBlank = true;
                for (String cell : row) {
                    if (cell != null && !cell.trim().isEmpty()) {
                        allBlank = false;
                        break;
                    }
                }
                if (allBlank) continue;

                if (row.length != 6) {
                    errors.add("Line " + lineNumber + ": Insufficient columns (expected 6)");
                    continue;
                }

                String name = row[0];
                String surname = row[1];
                String email = row[2];
                String company = row[3];

                Position position;
                try {
                    position = Position.valueOf(row[4].trim().toUpperCase().replace(' ', '_'));
                } catch (IllegalArgumentException e) {
                    errors.add("Line " + lineNumber + ": Invalid position for employee");
                    continue;
                }

                Double salary;
                try {
                    salary = Double.parseDouble(row[5]);
                    if (salary < 0) {
                        errors.add("Line " + lineNumber + ": Salary cannot be negative");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    errors.add("Line " + lineNumber + ": Invalid salary format (expected numeric)");
                    continue;
                }

                Employee employee = new Employee(name, surname, email, company, position, salary);
                employeeService.addEmployee(employee);
                importedCount++;
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Failed to read CSV: " + filePath, e);
        }

        return new ImportSummary(importedCount, errors);
    }

    private Reader getReaderForPath(String filePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        if (resource.exists()) {
            return new InputStreamReader(resource.getInputStream());
        }
        return new FileReader(filePath);
    }
}
