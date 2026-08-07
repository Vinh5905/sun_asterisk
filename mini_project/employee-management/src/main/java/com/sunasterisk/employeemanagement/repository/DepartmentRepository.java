package com.sunasterisk.employeemanagement.repository;

import com.sunasterisk.employeemanagement.model.Department;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByNameIgnoreCase(String name);
}
