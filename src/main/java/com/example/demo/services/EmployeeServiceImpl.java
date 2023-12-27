package com.example.demo.services;

import com.example.demo.Exceptions.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepo employeeRepo ;

    @Autowired
    private EmailService emailService;

    @Override
    public String addEmployee(Employee employee) {
        // Generate a unique UUID for the employee
        String employeeId = UUID.randomUUID().toString();
        employee.setId(employeeId);

        // Save the employee to the database
        Employee savedEmployee = employeeRepo.save(employee);

        // Send email to level 1 manager
        sendEmailToLevel1Manager(savedEmployee);

        // Return the generated employee ID
        return employeeId;
    }

    private void sendEmailToLevel1Manager(Employee employee) {
        // Your logic to determine the level 1 manager's email
        String level1ManagerEmail = getLevel1ManagerEmail(employee.getReportsTo());

        if (level1ManagerEmail != null) {
            // Compose the email body
            String emailBody = String.format(
                    "%s will now work under you. Mobile number is %s and email is %s",
                    employee.getEmployeeName(), employee.getPhoneNumber(), employee.getEmail()
            );

            // Send the email
            emailService.sendEmail(level1ManagerEmail, "New Employee Addition", emailBody);
        }
    }

    private String getLevel1ManagerEmail(String reportsTo) {
        // Initialize a counter to track the level
        int level = 0;

        // Assuming your employee IDs are unique strings, not numeric
        Set<String> visited = new HashSet<>();

        // Start from the current employee and traverse up the hierarchy
        return findLevel1ManagerEmail(reportsTo, level, visited);
    }

    private String findLevel1ManagerEmail(String employeeId, int level, Set<String> visited) {
        // Avoid infinite loops in case of cyclic dependencies
        if (visited.contains(employeeId) || level > 10) { // Adjust the maximum level as needed
            return null;
        }

        // Mark the current employee as visited
        visited.add(employeeId);

        // Retrieve the current employee from the database (assuming you have a repository)
        Employee employee = employeeRepo.findById(employeeId).orElse(null);

        // If the current employee or their manager doesn't exist, return null
        if (employee == null || employee.getReportsTo() == null) {
            return null;
        }

        // If the current employee is a level 1 manager, return their email
        if (level == 1) {
            return employee.getEmail();
        }

        // Recursively find the manager's email
        return findLevel1ManagerEmail(employee.getReportsTo(), level + 1, visited);
    }




    @Override
    public List<Employee> getAllEmployees() {

        return employeeRepo.findAll();
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        return null;
    }

    public Employee updateEmployee(String employeeId, Employee updatedEmployee) {
        // Check if the employee with the given ID exists
        Employee existingEmployee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));

        // Update the details of the existing employee
        existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
        existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setReportsTo(updatedEmployee.getReportsTo());
        existingEmployee.setProfileImage(updatedEmployee.getProfileImage());

        // Save the updated employee to the database
        return employeeRepo.save(existingEmployee);
    }



    public void deleteEmployee(String employeeId) {
        // Check if the employee with the given ID exists
        if (!employeeRepo.existsById(employeeId)) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + employeeId);
        }

        // Delete the employee from the database
        employeeRepo.deleteById(employeeId);
    }

// --------------------- Intermediate --------------
    public Employee getNthLevelManager(String employeeId, int level) {
            // Check if the employee with the given ID exists
            Employee employee = employeeRepo.findById(employeeId)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));

            // Get the nth level manager of the employee
            return findNthLevelManager(employee, level);
        }

        private Employee findNthLevelManager(Employee employee, int level) {
            if (level <= 0) {
                // Invalid level, return the employee itself
                return employee;
            }

            String reportsToId = employee.getReportsTo();

            if (reportsToId == null) {
                // No manager at this level, return null
                return null;
            }

            // Retrieve the manager from the database
            Employee manager = employeeRepo.findById(reportsToId)
                    .orElseThrow(() -> new EmployeeNotFoundException("Manager not found with ID: " + reportsToId));

            // Recursively find the nth level manager
            return findNthLevelManager(manager, level - 1);
        }


    public List<Employee> getEmployeesWithPaginationAndSorting(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Employee> employeePage = employeeRepo.findAll(pageRequest);
        return employeePage.getContent();
    }


}
