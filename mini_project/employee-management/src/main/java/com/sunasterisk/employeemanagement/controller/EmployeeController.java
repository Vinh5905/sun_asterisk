package com.sunasterisk.employeemanagement.controller;

import com.sunasterisk.employeemanagement.dto.EmployeeRequest;
import com.sunasterisk.employeemanagement.model.Employee;
import com.sunasterisk.employeemanagement.service.EmployeeService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/search")
    public List<Employee> searchEmployees(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String department
    ) {
        return employeeService.searchEmployees(name, department);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeRequest request) {
        Employee employee = employeeService.createEmployee(request);
        return ResponseEntity
            .created(URI.create("/api/employees/" + employee.getId()))
            .body(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(
        @PathVariable Long id,
        @RequestBody EmployeeRequest request
    ) {
        return employeeService.updateEmployee(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
