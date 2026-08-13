package com.sunasterisk.employeemanagement.dto.report;

import java.util.List;

public record EmployeeStatisticsResponse(
    long totalEmployees,
    List<DepartmentEmployeeStatisticResponse> employeesByDepartment
) {
}
