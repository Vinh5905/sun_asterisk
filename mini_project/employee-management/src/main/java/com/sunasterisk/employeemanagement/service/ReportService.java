package com.sunasterisk.employeemanagement.service;

import com.sunasterisk.employeemanagement.dto.report.DepartmentEmployeeStatisticResponse;
import com.sunasterisk.employeemanagement.repository.EmployeeRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    private final EmployeeRepository employeeRepository;

    public ReportService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Cacheable(value = "employeeReports", key = "'totalEmployees'")
    public long getTotalEmployees() {
        logger.info("Calculating total employees report");
        return employeeRepository.count();
    }

    @Cacheable(value = "employeeReports", key = "'employeesByDepartment'")
    public List<DepartmentEmployeeStatisticResponse> getEmployeesByDepartment() {
        logger.info("Calculating employees by department report");
        return employeeRepository.countEmployeesByDepartment()
            .stream()
            .map(item -> new DepartmentEmployeeStatisticResponse(
                item.getDepartmentName(),
                item.getEmployeeCount()
            ))
            .toList();
    }
}
