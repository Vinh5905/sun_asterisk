package com.sunasterisk.employeemanagement.dto;

public record EmployeeRequest(
    String fullName,
    String department,
    String position,
    String email
) {
}
