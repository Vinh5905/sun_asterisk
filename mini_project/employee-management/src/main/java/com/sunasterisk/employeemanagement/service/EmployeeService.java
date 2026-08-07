package com.sunasterisk.employeemanagement.service;

import com.sunasterisk.employeemanagement.dto.EmployeeRequest;
import com.sunasterisk.employeemanagement.model.Department;
import com.sunasterisk.employeemanagement.model.Employee;
import java.util.List;
import com.sunasterisk.employeemanagement.repository.DepartmentRepository;
import com.sunasterisk.employeemanagement.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UtilityService utilityService;

    public EmployeeService(
        EmployeeRepository employeeRepository,
        DepartmentRepository departmentRepository,
        UtilityService utilityService
    ) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.utilityService = utilityService;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Employee not found"
            ));
    }

    public List<Employee> searchEmployees(String name, String department) {
        return employeeRepository.search(emptyToNull(name), emptyToNull(department));
    }

    public Employee createEmployee(EmployeeRequest request) {
        Department department = getDepartmentById(request.departmentId());
        Employee employee = new Employee(
            utilityService.formatName(request.name()),
            utilityService.normalizeText(request.email()),
            department
        );

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = getEmployeeById(id);
        Department department = getDepartmentById(request.departmentId());

        employee.setName(utilityService.formatName(request.name()));
        employee.setEmail(utilityService.normalizeText(request.email()));
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    private Department getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Department not found"
            ));
    }

    private String emptyToNull(String value) {
        String normalizedValue = utilityService.normalizeText(value);
        return normalizedValue.isEmpty() ? null : normalizedValue;
    }
}
