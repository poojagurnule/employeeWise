package com.example.demo.controller;

import com.example.demo.Exceptions.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.services.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;



@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;



    @PostMapping
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        try {
            // Add the employee and get the generated ID
            String employeeId = employeeService.addEmployee(employee);

            // Return the generated ID in the response
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding employee: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            // Retrieve all employees from the service
            List<Employee> employees = employeeService.getAllEmployees();

            // Return the list of employees in the response
            return ResponseEntity.status(HttpStatus.OK).body(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
        try {
            Employee employee = employeeService.getEmployeeById(employeeId);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String employeeId, @RequestBody Employee updatedEmployee) {
        try {
            // Update the employee by ID
            Employee updatedEmployeeResult = employeeService.updateEmployee(employeeId, updatedEmployee);

            // Return the updated employee in the response
            return ResponseEntity.status(HttpStatus.OK).body(updatedEmployeeResult);
        } catch (EmployeeNotFoundException e) {
            // Handle the case where the employee with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Handle other exceptions (e.g., validation errors, database errors)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId) {
        try {
            // Delete the employee by ID
            employeeService.deleteEmployee(employeeId);

            // Return a success response with no content
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EmployeeNotFoundException e) {
            // Handle the case where the employee with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Handle other exceptions (e.g., database errors)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    // ------------------------------ Intermediate --------------------

    @GetMapping("/{employeeId}/manager")
    public ResponseEntity<Employee> getNthLevelManager(
            @PathVariable String employeeId,
            @RequestParam(name = "level", defaultValue = "1") int level
    ) {
        try {
            // Get the nth level manager of the employee
            Employee manager = employeeService.getNthLevelManager(employeeId, level);

            // Return the manager in the response
            return ResponseEntity.status(HttpStatus.OK).body(manager);
        } catch (EmployeeNotFoundException e) {
            // Handle the case where the employee with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Handle other exceptions (e.g., validation errors, database errors)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





    @GetMapping("/paged")
    public List<Employee> getEmployeesWithPaginationAndSorting(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy
    ) {
        if (page == null || size == null) {
            // If page or size is not provided, return all employees without pagination
            System.out.println("No pagination parameters provided.");


            List<Employee> allEmployees = employeeService.getAllEmployees();
            if (sortBy != null) {
                allEmployees.sort(Comparator.comparing(Employee::getEmployeeName));
            }

            return allEmployees;
        }

        System.out.println("Page: " + page + ", Size: " + size + ", SortBy: " + sortBy);
        return employeeService.getEmployeesWithPaginationAndSorting(page, size, sortBy);
    }


}

