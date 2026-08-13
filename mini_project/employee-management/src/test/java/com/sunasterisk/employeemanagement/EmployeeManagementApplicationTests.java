package com.sunasterisk.employeemanagement;

import com.sunasterisk.employeemanagement.model.AppUser;
import com.sunasterisk.employeemanagement.repository.AppUserRepository;
import com.sunasterisk.employeemanagement.security.JwtService;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@WithMockUser(roles = "ADMIN")
class EmployeeManagementApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private JwtService jwtService;

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
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Employee not found with id: 2"));
    }

    @Test
    void createEmployeeReturnsValidationErrors() throws Exception {
        String requestBody = """
            {
              "name": "",
              "email": "invalid-email",
              "departmentId": null
            }
            """;

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Request validation failed"))
            .andExpect(jsonPath("$.fieldErrors.name", notNullValue()))
            .andExpect(jsonPath("$.fieldErrors.email").value("Email must be valid"))
            .andExpect(jsonPath("$.fieldErrors.departmentId").value("Department id must not be null"));
    }

    @Test
    void getEmployeeReturnsNotFoundMessage() throws Exception {
        mockMvc.perform(get("/api/employees/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Employee not found with id: 999"));
    }

    @Test
    void createEmployeeReturnsClearMessageForWrongDataFormat() throws Exception {
        String requestBody = """
            {
              "name": "Le Van Cuong",
              "email": "cuong.le@example.com",
              "departmentId": "abc"
            }
            """;

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Request body is invalid or has wrong data format"));
    }

    @Test
    void employeesListPageShowsDbEmployees() throws Exception {
        mockMvc.perform(get("/employees/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("employees/list"))
            .andExpect(model().attributeExists("employees"));
    }

    @Test
    void addEmployeePageShowsFormAndDepartments() throws Exception {
        mockMvc.perform(get("/employees/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("employees/add"))
            .andExpect(model().attributeExists("employeeForm"))
            .andExpect(model().attributeExists("departments"));
    }

    @Test
    void addEmployeeFormCreatesEmployeeAndRedirects() throws Exception {
        mockMvc.perform(post("/employees/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Nguyen Van Nam")
                .param("email", "nam.nguyen@example.com")
                .param("departmentId", "1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/employees/list"));
    }

    @Test
    void addEmployeeFormShowsValidationErrors() throws Exception {
        mockMvc.perform(post("/employees/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "")
                .param("email", "invalid-email")
                .param("departmentId", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("employees/add"))
            .andExpect(model().attributeHasFieldErrors(
                "employeeForm",
                "name",
                "email",
                "departmentId"
            ))
            .andExpect(model().attributeExists("departments"));
    }

    @Test
    void searchEmployeePageShowsResults() throws Exception {
        mockMvc.perform(get("/employees/search")
                .param("department", "Engineering"))
            .andExpect(status().isOk())
            .andExpect(view().name("employees/search"))
            .andExpect(model().attributeExists("employees"))
            .andExpect(model().attribute("department", "Engineering"));
    }

    @Test
    void employeeTotalReportReturnsCachedCount() throws Exception {
        mockMvc.perform(get("/api/reports/employees/total"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalEmployees", greaterThanOrEqualTo(2)));
    }

    @Test
    void actuatorEndpointsAreAvailable() throws Exception {
        mockMvc.perform(get("/actuator/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("UP"));

        mockMvc.perform(get("/actuator/metrics"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.names", notNullValue()));
    }

    @Test
    @WithAnonymousUser
    void loginReturnsJwtToken() throws Exception {
        String requestBody = """
            {
              "username": "admin",
              "password": "admin123"
            }
            """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("admin"))
            .andExpect(jsonPath("$.role").value("ADMIN"))
            .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    @WithAnonymousUser
    void registerCreatesUserAccount() throws Exception {
        String requestBody = """
            {
              "username": "newuser",
              "password": "newpass123",
              "role": "USER"
            }
            """;

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.username").value("newuser"))
            .andExpect(jsonPath("$.role").value("USER"))
            .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    @WithAnonymousUser
    void basicAuthenticationAllowsUserToViewEmployees() throws Exception {
        mockMvc.perform(get("/api/employees").with(httpBasic("user", "user123")))
            .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void jwtAuthenticationAllowsUserToViewEmployees() throws Exception {
        AppUser user = appUserRepository.findByUsername("user").orElseThrow();
        String token = jwtService.generateToken(user);

        mockMvc.perform(get("/api/employees")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void userCannotCreateEmployee() throws Exception {
        String requestBody = """
            {
              "name": "Le Van Cuong",
              "email": "cuong.forbidden@example.com",
              "departmentId": 1
            }
            """;

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isForbidden());
    }
}
