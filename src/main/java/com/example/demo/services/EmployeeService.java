package com.example.demo.services;

import com.example.demo.model.Employee;

import java.util.List;

public interface EmployeeService {

   

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long employeeId);

    Employee updateEmployee(String employeeId, Employee updatedEmployee);

    void deleteEmployee(String employeeId);

    String addEmployee(Employee employee);

    Employee getNthLevelManager(String employeeId, int level);

    List<Employee> getEmployeesWithPaginationAndSorting(int page, int size, String sortBy);
}
