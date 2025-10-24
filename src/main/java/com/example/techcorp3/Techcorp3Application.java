package com.example.techcorp3;

import com.example.techcorp3.model.Employee;
import com.example.techcorp3.model.Position;
import com.example.techcorp3.model.ImportSummary;
import com.example.techcorp3.service.ApiService;
import com.example.techcorp3.service.EmployeeService;
import com.example.techcorp3.service.ImportService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@ImportResource("classpath:employees-beans.xml")
public class Techcorp3Application implements CommandLineRunner {
    private final EmployeeService employeeService;
    private final ApiService apiService;
    private final ImportService importService;
    private final List<Employee> xmlEmployees;

    public Techcorp3Application(EmployeeService employeeService,
                                ApiService apiService,
                                ImportService importService,
                                @Qualifier("xmlEmployees") List<Employee> xmlEmployees) {
        System.out.println("Techcorp3Application initialized with EmployeeService, ApiService, ImportService and xmlEmployees");
        this.employeeService = employeeService;
        this.apiService = apiService;
        this.importService = importService;
        this.xmlEmployees = xmlEmployees;
    }

    public static void main(String[] args) {
        SpringApplication.run(Techcorp3Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Choose an option:");
            System.out.println("1. List all employees");
            System.out.println("2. Add new employee");
            System.out.println("3. Get employees by company");
            System.out.println("4. Sort employees by surname");
            System.out.println("5. Group employees by position");
            System.out.println("6. Count employees by position");
            System.out.println("7. Calculate average salary");
            System.out.println("8. Get employee with highest salary");
            System.out.println("9. Get employees from API");
            System.out.println("10. Validate salary consistency");
            System.out.println("11. Get company statistics");
            System.out.println("12. Import employees from CSV");
            System.out.println("13. Add employees from XML configuration");
            System.out.println("0. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> System.out.println(employeeService.getAllEmployees());
                case 2 -> {
                    System.out.println("Enter first name:");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter last name:");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter company:");
                    String company = scanner.nextLine();
                    System.out.println("Enter position (PREZES, WICEPREZES, MANAGER, PROGRAMISTA, STAZYSTA):");
                    Position position = null;
                    while (position == null) {
                        String posInput = scanner.nextLine().toUpperCase();
                        try {
                            position = Position.valueOf(posInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid position. Please enter one of: PREZES, WICEPREZES, MANAGER, PROGRAMISTA, STAZYSTA");
                        }
                    }

                    System.out.println("Enter salary:");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a valid number for salary:");
                        scanner.nextLine(); // discard invalid input
                    }
                    Double salary = scanner.nextDouble();
                    scanner.nextLine();
                    Employee newEmployee = new Employee(firstName, lastName, email, company, position, salary);
                    employeeService.addEmployee(newEmployee);
                }
                case 3 -> {
                    System.out.println("Enter company name:");
                    String company = scanner.nextLine();
                    System.out.println(employeeService.getEmployeesByCompany(company));
                }
                case 4 -> System.out.println(employeeService.sortEmployeesBySurname());
                case 5 -> System.out.println(employeeService.groupEmployeesByPosition());
                case 6 -> System.out.println(employeeService.countEmployeesByPosition());
                case 7 -> System.out.println("Average salary: " + employeeService.calculateAverageSalary());
                case 8 -> employeeService.getEmployeeWithHighestSalary()
                        .ifPresentOrElse(employee -> System.out.println("Employee with highest salary: " + employee),
                                () -> System.out.println("No employees found"));
                case 9 -> {
                    try {
                        List<Employee> employees = apiService.fetchEmployeesFromApi();
                        System.out.println(employees);
                    } catch (Exception e) {
                        System.out.println("Error fetching employees from API: " + e.getMessage());
                    }
                }
                case 10 -> {
                    List<Employee> underpaid = employeeService.validateSalaryConsistency();
                    System.out.println(underpaid);
                }
                case 11 -> {
                    System.out.println(employeeService.getCompanyStatistics());
                }
                case 12 -> {
                    ImportSummary importSummary = importService.importFromCsv();
                    System.out.println("Import Summary: " + importSummary);
                }
                case 13 -> {
                    System.out.println("Adding employees from XML configuration...");
                    for (Employee employee : xmlEmployees) {
                        employeeService.addEmployee(employee);
                    }
                    System.out.println(employeeService.getAllEmployees());
                }
                case 0 -> running = false;
            }
        }
        scanner.close();
    }
}
