package com.sunasterisk.employeemanagement.dto.report;

public record DepartmentEmployeeStatisticResponse(
    String departmentName,
    long employeeCount
) {
}
