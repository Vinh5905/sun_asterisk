package com.sunasterisk.employeemanagement.controller;

import com.sunasterisk.employeemanagement.dto.EmployeeRequest;
import com.sunasterisk.employeemanagement.model.Employee;
import com.sunasterisk.employeemanagement.service.EmployeeService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeRequest request) {
        Employee employee = employeeService.createEmployee(request);
        return ResponseEntity
            .created(URI.create("/api/employees/" + employee.id()))
            .body(employee);
    }
}
