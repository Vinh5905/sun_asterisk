package com.sunasterisk.employeemanagement.controller;

import com.sunasterisk.employeemanagement.dto.EmployeeForm;
import com.sunasterisk.employeemanagement.service.EmployeeService;
import com.sunasterisk.employeemanagement.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/employees")
public class EmployeeWebController {

    private final EmployeeService employeeService;
    private final ReportService reportService;

    public EmployeeWebController(EmployeeService employeeService, ReportService reportService) {
        this.employeeService = employeeService;
        this.reportService = reportService;
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employeeForm", new EmployeeForm());
        addDepartments(model);
        return "employees/add";
    }

    @PostMapping("/add")
    public String addEmployee(
        @Valid @ModelAttribute("employeeForm") EmployeeForm employeeForm,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            addDepartments(model);
            return "employees/add";
        }

        employeeService.createEmployee(employeeForm.toEmployeeRequest());
        return "redirect:/employees/list";
    }

    @GetMapping("/search")
    public String searchEmployees(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String department,
        Model model
    ) {
        model.addAttribute("name", name);
        model.addAttribute("department", department);
        model.addAttribute("employees", employeeService.searchEmployees(name, department));
        return "employees/search";
    }

    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        model.addAttribute("totalEmployees", reportService.getTotalEmployees());
        model.addAttribute("employeesByDepartment", reportService.getEmployeesByDepartment());
        return "employees/statistics";
    }

    private void addDepartments(Model model) {
        model.addAttribute("departments", employeeService.getAllDepartments());
    }
}
