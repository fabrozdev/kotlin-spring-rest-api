package com.example.spring_kotlin_rest_api.features.employee.repository

import com.example.spring_kotlin_rest_api.features.employee.model.entity.Employee
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface EmployeeRepository: MongoRepository<Employee, ObjectId> {
}
