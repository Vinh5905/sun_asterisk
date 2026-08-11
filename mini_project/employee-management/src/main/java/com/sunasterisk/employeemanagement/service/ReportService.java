package com.sunasterisk.employeemanagement.service;

import com.sunasterisk.employeemanagement.repository.EmployeeRepository;
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

    @Cacheable("employeeReports")
    public long getTotalEmployees() {
        logger.info("Calculating total employees report");
        return employeeRepository.count();
    }
}
