package com.example.spring_kotlin_rest_api.features.employee.model.entity

enum class Role(val displayName: String) {
    TRAINEE("Trainee"),
    REGULAR("Regular"),
    MANAGER("Manager"),
    ADMIN("Administrator");

    override fun toString(): String = displayName
}
