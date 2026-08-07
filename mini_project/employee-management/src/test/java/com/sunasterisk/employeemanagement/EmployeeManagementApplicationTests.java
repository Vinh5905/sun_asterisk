package com.sunasterisk.employeemanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class EmployeeManagementApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void getEmployeesReturnsInMemoryEmployees() throws Exception {
        mockMvc.perform(get("/api/employees"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
            .andExpect(jsonPath("$[0].employeeCode").value("EMP00001"))
            .andExpect(jsonPath("$[0].fullName").value("Nguyen Van An"));
    }

    @Test
    void createEmployeeAddsNewEmployee() throws Exception {
        String requestBody = """
            {
              "fullName": "Le Van Cuong",
              "department": "Engineering",
              "position": "Frontend Developer",
              "email": "cuong.le@example.com"
            }
            """;

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/api/employees/3"))
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.employeeCode").value("EMP00003"))
            .andExpect(jsonPath("$.fullName").value("Le Van Cuong"));
    }
}
