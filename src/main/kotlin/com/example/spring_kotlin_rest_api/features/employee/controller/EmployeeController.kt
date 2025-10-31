package com.example.spring_kotlin_rest_api.features.employee.controller

import com.example.spring_kotlin_rest_api.features.employee.model.dto.CreateEmployeeDto
import com.example.spring_kotlin_rest_api.features.employee.model.dto.EmployeeDto
import com.example.spring_kotlin_rest_api.features.employee.model.entity.Employee
import com.example.spring_kotlin_rest_api.features.employee.repository.EmployeeRepository
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/employees")
class EmployeeController(private val employeeRepository: EmployeeRepository) {
    val log: Logger = LoggerFactory.getLogger(EmployeeController::class.java)

    @GetMapping
    fun getEmployees(): List<String> {
        log.info("Getting employees")
        return employeeRepository.findAll().map { "${it.firstName} ${it.lastName}" }
    }

    @PostMapping
    fun createEmployee(@RequestBody employee: CreateEmployeeDto): EmployeeDto {
        log.info("Creating new employee")
        val employee = employeeRepository.save(Employee(
            role = employee.role,
            firstName = employee.firstName,
            lastName = employee.lastName,
            email = employee.email,
            createdAt = Instant.now(),
            id = ObjectId.get()
        ));

        return EmployeeDto(
            role = employee.role,
            firstName = employee.firstName,
            lastName = employee.lastName,
            email = employee.email,
            id = employee.id.toHexString()
        )
    }
}
