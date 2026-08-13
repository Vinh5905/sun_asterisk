package com.sunasterisk.employeemanagement.component;

import com.sunasterisk.employeemanagement.dto.EmployeeRequest;
import com.sunasterisk.employeemanagement.model.AppUser;
import com.sunasterisk.employeemanagement.model.Department;
import com.sunasterisk.employeemanagement.model.Role;
import com.sunasterisk.employeemanagement.repository.AppUserRepository;
import com.sunasterisk.employeemanagement.repository.DepartmentRepository;
import com.sunasterisk.employeemanagement.repository.EmployeeRepository;
import com.sunasterisk.employeemanagement.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final AppUserRepository appUserRepository;
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(
        DepartmentRepository departmentRepository,
        EmployeeRepository employeeRepository,
        AppUserRepository appUserRepository,
        EmployeeService employeeService,
        PasswordEncoder passwordEncoder
    ) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.appUserRepository = appUserRepository;
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
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

        createUserIfNotExists("admin", "admin123", Role.ADMIN);
        createUserIfNotExists("user", "user123", Role.USER);
    }

    private Department createDepartmentIfNotExists(String name) {
        return departmentRepository.findByNameIgnoreCase(name)
            .orElseGet(() -> departmentRepository.save(new Department(name)));
    }

    private void createUserIfNotExists(String username, String password, Role role) {
        if (!appUserRepository.existsByUsername(username)) {
            appUserRepository.save(new AppUser(
                username,
                passwordEncoder.encode(password),
                role
            ));
        }
    }
}
