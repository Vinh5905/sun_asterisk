package com.sunasterisk.employeemanagement.controller;

import com.sunasterisk.employeemanagement.dto.report.DepartmentEmployeeStatisticResponse;
import com.sunasterisk.employeemanagement.dto.report.EmployeeStatisticsResponse;
import com.sunasterisk.employeemanagement.service.ReportService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/employees/total")
    public Map<String, Long> getTotalEmployees() {
        return Map.of("totalEmployees", reportService.getTotalEmployees());
    }

    @GetMapping("/employees/by-department")
    public List<DepartmentEmployeeStatisticResponse> getEmployeesByDepartment() {
        return reportService.getEmployeesByDepartment();
    }

    @GetMapping("/employees/statistics")
    public EmployeeStatisticsResponse getEmployeeStatistics() {
        return new EmployeeStatisticsResponse(
            reportService.getTotalEmployees(),
            reportService.getEmployeesByDepartment()
        );
    }
}
