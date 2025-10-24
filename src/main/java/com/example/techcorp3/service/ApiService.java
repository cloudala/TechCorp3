package com.example.techcorp3.service;

import com.example.techcorp3.exception.ApiException;
import com.example.techcorp3.model.Employee;
import com.example.techcorp3.model.Position;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class ApiService {
    private final EmployeeService employeeService;
    private final HttpClient httpClient;
    private final Gson gson;
    private final String apiUrl;

    public ApiService(EmployeeService employeeService, HttpClient httpClient,
                      Gson gson, @Value("${app.api.url}") String apiUrl) {
        System.out.println("Api service initialized with EmployeeService, HttpClient and Gson dependencies and apiUrl from application.properties.");
        this.employeeService = employeeService;
        this.httpClient = httpClient;
        this.gson = gson;
        this.apiUrl = apiUrl;
    }

    public List<Employee> fetchEmployeesFromApi() throws IOException, InterruptedException, ApiException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonArray employeesJson = gson.fromJson(response.body(), JsonArray.class);

            for (JsonElement employeeJson : employeesJson) {
                JsonObject employeeJsonObject = employeeJson.getAsJsonObject();
                String fullName = employeeJsonObject.get("name").getAsString();
                String name = fullName.split(" ")[0];
                String surname = fullName.split(" ")[1];
                String email = employeeJsonObject.get("email").getAsString();
                String company = employeeJsonObject.getAsJsonObject("company").get("name").getAsString();
                Position position = Position.PROGRAMISTA;
                Double salary = Position.PROGRAMISTA.getbaseSalary();
                Employee employee = new Employee(name, surname, email, company, position, salary);
                employeeService.addEmployee(employee); // Save employees to global list
            }
            return employeeService.getAllEmployees();
        } else {
            throw new ApiException("HTTP error: " + response.statusCode());
        }
    }
}
