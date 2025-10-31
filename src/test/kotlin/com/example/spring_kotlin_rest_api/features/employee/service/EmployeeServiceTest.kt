package com.example.spring_kotlin_rest_api.features.employee.service

import com.example.spring_kotlin_rest_api.features.employee.model.dto.CreateEmployeeDto
import com.example.spring_kotlin_rest_api.features.employee.model.dto.UpdateEmployeeDto
import com.example.spring_kotlin_rest_api.features.employee.model.entity.Employee
import com.example.spring_kotlin_rest_api.features.employee.model.entity.Role
import com.example.spring_kotlin_rest_api.features.employee.repository.EmployeeRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.Instant

@ExtendWith(MockKExtension::class)
class EmployeeServiceTest {
    private val employeeRepository = mockk<EmployeeRepository>()

    @InjectMockKs
    lateinit var employeeService: EmployeeService

    @Test
    fun `should list employees`() {
        val employees = listOf(
            Employee(
                firstName = "John",
                lastName = "Doe",
                email = "john@example.com",
                role = Role.MANAGER,
                createdAt = Instant.now()
            )
        )

        every { employeeRepository.findByDeletedAtIsNull() } returns employees
        val result = employeeService.getAllEmployees();
        assertEquals(1, result.size);
    }

    @Test
    fun `should create new employee`() {
        val createDto = CreateEmployeeDto(
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com",
            role = Role.MANAGER
        )

        every { employeeRepository.save(any<Employee>()) } answers {
            val employee = firstArg<Employee>()
            employee // Return the employee that was passed in
        }

        val result = employeeService.createEmployee(createDto)

        assertEquals("John", result.firstName)
        assertEquals("Doe", result.lastName)
        assertEquals("john@example.com", result.email)
        assertEquals(Role.MANAGER, result.role)
        assertNotNull(result.id)

        verify { employeeRepository.save(any<Employee>()) }
    }

    @Test
    fun `should update employee`() {
        val employeeId = ObjectId.get()
        val existingEmployee = Employee(
            id = employeeId,
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com",
            role = Role.MANAGER,
            createdAt = Instant.now()
        )

        val updateDto = UpdateEmployeeDto(
            firstName = "Johnny",
            lastName = "Smith",
            email = "johnny.smith@example.com",
            role = Role.ADMIN
        )

        every { employeeRepository.findByIdAndDeletedAtIsNull(employeeId) } returns existingEmployee
        every { employeeRepository.save(any<Employee>()) } answers {
            firstArg<Employee>()
        }

        val result = employeeService.updateEmployee(employeeId.toHexString(), updateDto)

        assertEquals("Johnny", result.firstName)
        assertEquals("Smith", result.lastName)
        assertEquals("johnny.smith@example.com", result.email)
        assertEquals(Role.ADMIN, result.role)

        verify { employeeRepository.findByIdAndDeletedAtIsNull(employeeId) }
        verify { employeeRepository.save(match {
            it.firstName == "Johnny" &&
                    it.lastName == "Smith" &&
                    it.updatedAt != null
        }) }
    }

    @Test
    fun `should update employee with partial data`() {
        val employeeId = ObjectId.get()
        val existingEmployee = Employee(
            id = employeeId,
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com",
            role = Role.MANAGER,
            createdAt = Instant.now()
        )

        val updateDto = UpdateEmployeeDto(
            firstName = "Johnny",
            lastName = null,  // Not updating
            email = null,     // Not updating
            role = null       // Not updating
        )

        every { employeeRepository.findByIdAndDeletedAtIsNull(employeeId) } returns existingEmployee
        every { employeeRepository.save(any<Employee>()) } answers {
            firstArg<Employee>()
        }

        val result = employeeService.updateEmployee(employeeId.toHexString(), updateDto)

        assertEquals("Johnny", result.firstName)
        assertEquals("Doe", result.lastName)  // Should remain unchanged
        assertEquals("john@example.com", result.email)  // Should remain unchanged
        assertEquals(Role.MANAGER, result.role)  // Should remain unchanged

        verify { employeeRepository.findByIdAndDeletedAtIsNull(employeeId) }
        verify { employeeRepository.save(any()) }
    }

    @Test
    fun `should throw exception when updating non-existent employee`() {
        val employeeId = ObjectId.get()
        val updateDto = UpdateEmployeeDto(
            firstName = "Johnny",
            lastName = null,
            email = null,
            role = null
        )

        every { employeeRepository.findByIdAndDeletedAtIsNull(employeeId) } returns null

        val exception = assertThrows<EmployeeNotFoundException> {
            employeeService.updateEmployee(employeeId.toHexString(), updateDto)
        }

        assertEquals("Employee with id: ${employeeId.toHexString()} not found", exception.message)
        verify { employeeRepository.findByIdAndDeletedAtIsNull(employeeId) }
        verify(exactly = 0) { employeeRepository.save(any()) }
    }

    @Test
    fun `should soft delete employee`() {
        val employeeId = ObjectId.get()
        val employee = Employee(
            id = employeeId,
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com",
            role = Role.MANAGER,
            createdAt = Instant.now(),
            deletedAt = null
        )

        every { employeeRepository.findByIdAndDeletedAtIsNull(employeeId) } returns employee
        every { employeeRepository.save(any<Employee>()) } answers {
            firstArg<Employee>()
        }

        val result = employeeService.softDeleteEmployee(employeeId.toHexString())

        assertTrue(result)
        verify { employeeRepository.findByIdAndDeletedAtIsNull(employeeId) }
        verify { employeeRepository.save(match { it.deletedAt != null }) }
    }

    @Test
    fun `should throw exception when soft deleting non-existent employee`() {
        val employeeId = ObjectId.get()

        every { employeeRepository.findByIdAndDeletedAtIsNull(employeeId) } returns null

        val exception = assertThrows<EmployeeNotFoundException> {
            employeeService.softDeleteEmployee(employeeId.toHexString())
        }

        assertEquals("Employee with id: ${employeeId.toHexString()} not found", exception.message)
        verify { employeeRepository.findByIdAndDeletedAtIsNull(employeeId) }
        verify(exactly = 0) { employeeRepository.save(any()) }
    }

}
