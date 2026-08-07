package com.sunasterisk.employeemanagement.component;

import com.sunasterisk.employeemanagement.dto.EmployeeRequest;
import com.sunasterisk.employeemanagement.model.Department;
import com.sunasterisk.employeemanagement.repository.DepartmentRepository;
import com.sunasterisk.employeemanagement.repository.EmployeeRepository;
import com.sunasterisk.employeemanagement.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    public DataSeeder(
        DepartmentRepository departmentRepository,
        EmployeeRepository employeeRepository,
        EmployeeService employeeService
    ) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

    @PostConstruct
    void seed() {
        Department engineering = createDepartmentIfNotExists("Engineering");
        Department humanResources = createDepartmentIfNotExists("Human Resources");

        if (employeeRepository.count() == 0) {
            employeeService.createEmployee(new EmployeeRequest(
                "Pham Hoang Vinh",
                "vinh.pham@gmail.com",
                engineering.getId()
            ));
            employeeService.createEmployee(new EmployeeRequest(
                "Tran Thi Binh",
                "binh.tran@example.com",
                humanResources.getId()
            ));
        }
    }

    private Department createDepartmentIfNotExists(String name) {
        return departmentRepository.findByNameIgnoreCase(name)
            .orElseGet(() -> departmentRepository.save(new Department(name)));
    }
}
