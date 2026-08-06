package com.sunasterisk.employeemanagement.controller;

import com.sunasterisk.employeemanagement.service.UtilityService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final UtilityService utilityService;

    public HelloController(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Employee Management System";
    }

    @GetMapping("/verify")
    public Map<String, Object> verify() {
        String samplePassword = "password123";
        String encodedPassword = utilityService.encodePassword(samplePassword);

        return Map.of(
            "message", "Employee Management System is running",
            "sampleEmployeeCode", utilityService.generateEmployeeCode(1),
            "passwordEncoderInjected", utilityService.passwordMatches(samplePassword, encodedPassword)
        );
    }
}
