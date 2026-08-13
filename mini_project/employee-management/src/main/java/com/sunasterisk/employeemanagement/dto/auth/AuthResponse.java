package com.sunasterisk.employeemanagement.dto.auth;

public record AuthResponse(
    String username,
    String role,
    String token
) {
}
