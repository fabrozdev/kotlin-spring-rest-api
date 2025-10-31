package com.example.spring_kotlin_rest_api.features.employee.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeController {
    @GetMapping("/employees")
    fun getEmployees(): List<String> {
        return listOf("Fabrizzio", "Samuel")
    }
}
