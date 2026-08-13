package com.sunasterisk.employeemanagement.repository;

import com.sunasterisk.employeemanagement.model.Employee;
import com.sunasterisk.employeemanagement.repository.projection.DepartmentEmployeeCount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("""
        select employee
        from Employee employee
        where (:name is null or lower(employee.name) like lower(concat('%', :name, '%')))
          and (:department is null or lower(employee.department.name) like lower(concat('%', :department, '%')))
        """)
    List<Employee> search(
        @Param("name") String name,
        @Param("department") String department
    );

    @Query("""
        select employee.department.name as departmentName,
               count(employee.id) as employeeCount
        from Employee employee
        group by employee.department.id, employee.department.name
        order by employee.department.name
        """)
    List<DepartmentEmployeeCount> countEmployeesByDepartment();
}
