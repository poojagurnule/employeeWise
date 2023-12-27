package com.example.demo.repo;

import com.example.demo.model.Employee;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee , String> {

}





