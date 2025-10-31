package com.example.spring_kotlin_rest_api.features.employee.controller

import com.example.spring_kotlin_rest_api.features.employee.model.dto.CreateEmployeeDto
import com.example.spring_kotlin_rest_api.features.employee.model.dto.EmployeeDto
import com.example.spring_kotlin_rest_api.features.employee.model.dto.UpdateEmployeeDto
import com.example.spring_kotlin_rest_api.features.employee.service.EmployeeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/employees")
class EmployeeController(private val employeeService: EmployeeService) {
    @GetMapping
    fun getEmployees(): List<EmployeeDto> {
        return employeeService.getAllEmployees()
    }

    @PostMapping
    fun createEmployee(@RequestBody employee: CreateEmployeeDto): EmployeeDto {
        return employeeService.createEmployee(employee)
    }

    @PutMapping("/{id}")
    fun editEmployee(@PathVariable id: String, @RequestBody employee: UpdateEmployeeDto): EmployeeDto {
        return employeeService.updateEmployee(id, employee)
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id: String): Boolean {
        return employeeService.softDeleteEmployee(id)
    }
}
