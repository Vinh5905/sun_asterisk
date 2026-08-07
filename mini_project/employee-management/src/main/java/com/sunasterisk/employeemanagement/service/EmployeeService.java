package com.sunasterisk.employeemanagement.service;

import com.sunasterisk.employeemanagement.dto.EmployeeRequest;
import com.sunasterisk.employeemanagement.model.Employee;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final UtilityService utilityService;

    public EmployeeService(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @PostConstruct
    void initData() {
        createEmployee(new EmployeeRequest(
            "Pham Hoang Vinh",
            "Engineering",
            "Data Engineering",
            "vinh.pham@gmail.com"
        ));
        createEmployee(new EmployeeRequest(
            "Tran Thi Binh",
            "Human Resources",
            "HR Specialist",
            "binh.tran@example.com"
        ));
    }

    public synchronized List<Employee> getAllEmployees() {
        return List.copyOf(employees);
    }

    public synchronized Employee createEmployee(EmployeeRequest request) {
        long id = idGenerator.incrementAndGet();
        Employee employee = new Employee(
            id,
            utilityService.generateEmployeeCode(id),
            utilityService.formatName(request.fullName()),
            utilityService.normalizeText(request.department()),
            utilityService.normalizeText(request.position()),
            utilityService.normalizeText(request.email())
        );

        employees.add(employee);
        return employee;
    }
}
