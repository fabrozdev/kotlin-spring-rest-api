package com.example.spring_kotlin_rest_api.features.employee.service

import com.example.spring_kotlin_rest_api.features.employee.controller.EmployeeController
import com.example.spring_kotlin_rest_api.features.employee.mapper.EmployeeMapper
import com.example.spring_kotlin_rest_api.features.employee.model.dto.CreateEmployeeDto
import com.example.spring_kotlin_rest_api.features.employee.model.dto.EmployeeDto
import com.example.spring_kotlin_rest_api.features.employee.model.dto.UpdateEmployeeDto
import com.example.spring_kotlin_rest_api.features.employee.model.entity.Employee
import com.example.spring_kotlin_rest_api.features.employee.repository.EmployeeRepository
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.time.Instant

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository
) {
    val log: Logger = LoggerFactory.getLogger(EmployeeController::class.java)

    fun getAllEmployees(): List<EmployeeDto> {
        log.info("Getting employees")
        return employeeRepository.findByDeletedAtIsNull().map(EmployeeMapper.INSTANCE::toDto)
    }

    fun createEmployee(@RequestBody employee: CreateEmployeeDto): EmployeeDto {
        log.info("Creating new employee")
        val employee = employeeRepository.save(Employee(
            role = employee.role,
            firstName = employee.firstName,
            lastName = employee.lastName,
            email = employee.email,
            createdAt = Instant.now(),
            id = ObjectId.get()
        ))

        return EmployeeMapper.INSTANCE.toDto(employee)
    }

    fun updateEmployee(id: String, updateDto: UpdateEmployeeDto): EmployeeDto {
        log.info("Updating employee with id: $id")
        val objectId = ObjectId(id)
        val employee = employeeRepository.findByIdAndDeletedAtIsNull(objectId)
            ?: throw EmployeeNotFoundException("Employee with id: $id not found")

        val updatedEmployee = employee.copy(
            firstName = updateDto.firstName ?: employee.firstName,
            lastName = updateDto.lastName ?: employee.lastName,
            email = updateDto.email ?: employee.email,
            role = updateDto.role ?: employee.role,
            updatedAt = Instant.now()
        )

        return EmployeeMapper.INSTANCE.toDto(employeeRepository.save(updatedEmployee))
    }

    fun softDeleteEmployee(id: String): Boolean {
        log.info("Soft deleting employee with id: $id")
        val objectId = ObjectId(id)
        val employee = employeeRepository.findByIdAndDeletedAtIsNull(objectId)
            ?: throw EmployeeNotFoundException("Employee with id: $id not found")

        val deletedEmployee = employee.copy(deletedAt = Instant.now())
        employeeRepository.save(deletedEmployee)

        return true
    }
}

class EmployeeNotFoundException(message: String) : RuntimeException(message)
