package com.example.spring_kotlin_rest_api.features.employee.model.dto

import com.example.spring_kotlin_rest_api.features.employee.model.entity.Role

data class CreateEmployeeDto (
    val id: String? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role
)
