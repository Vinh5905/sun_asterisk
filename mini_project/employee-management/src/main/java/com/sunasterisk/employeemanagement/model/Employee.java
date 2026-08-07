package com.sunasterisk.employeemanagement.model;

public record Employee(
    long id,
    String employeeCode,
    String fullName,
    String department,
    String position,
    String email
) {
}
