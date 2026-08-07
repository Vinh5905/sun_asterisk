package com.sunasterisk.employeemanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class EmployeeManagementApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void getEmployeesReturnsDbEmployees() throws Exception {
        mockMvc.perform(get("/api/employees"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Pham Hoang Vinh"))
            .andExpect(jsonPath("$[0].department.name").value("Engineering"));
    }

    @Test
    void createEmployeeAddsNewEmployee() throws Exception {
        String requestBody = """
            {
              "name": "Le Van Cuong",
              "email": "cuong.le@example.com",
              "departmentId": 1
            }
            """;

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", startsWith("/api/employees/")))
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.name").value("Le Van Cuong"))
            .andExpect(jsonPath("$.email").value("cuong.le@example.com"))
            .andExpect(jsonPath("$.department.id").value(1));
    }

    @Test
    void searchEmployeesByNameOrDepartment() throws Exception {
        mockMvc.perform(get("/api/employees/search")
                .param("department", "Engineering"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
            .andExpect(jsonPath("$[0].name", notNullValue()))
            .andExpect(jsonPath("$[0].department.name").value("Engineering"));

        mockMvc.perform(get("/api/employees/search")
                .param("name", "Binh"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
            .andExpect(jsonPath("$[0].name").value("Tran Thi Binh"));
    }

    @Test
    void updateEmployeeChangesDbRecord() throws Exception {
        String requestBody = """
            {
              "name": "Pham Hoang Vinh Updated",
              "email": "vinh.updated@example.com",
              "departmentId": 2
            }
            """;

        mockMvc.perform(put("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Pham Hoang Vinh Updated"))
            .andExpect(jsonPath("$.email").value("vinh.updated@example.com"))
            .andExpect(jsonPath("$.department.id").value(2));
    }

    @Test
    void deleteEmployeeRemovesDbRecord() throws Exception {
        mockMvc.perform(delete("/api/employees/2"))
            .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/employees/2"))
            .andExpect(status().isNotFound());
    }
}
