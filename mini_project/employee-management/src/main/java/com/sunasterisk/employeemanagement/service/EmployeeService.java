package com.sunasterisk.employeemanagement.service;

import com.sunasterisk.employeemanagement.dto.EmployeeRequest;
import com.sunasterisk.employeemanagement.exception.ResourceNotFoundException;
import com.sunasterisk.employeemanagement.model.Department;
import com.sunasterisk.employeemanagement.model.Employee;
import java.util.List;
import com.sunasterisk.employeemanagement.repository.DepartmentRepository;
import com.sunasterisk.employeemanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

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

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
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
            .orElseThrow(() -> new ResourceNotFoundException(
                "Department not found with id: " + departmentId
            ));
    }

    private String emptyToNull(String value) {
        String normalizedValue = utilityService.normalizeText(value);
        return normalizedValue.isEmpty() ? null : normalizedValue;
    }
}
