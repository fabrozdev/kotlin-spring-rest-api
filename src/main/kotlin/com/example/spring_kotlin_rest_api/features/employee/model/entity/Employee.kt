package com.example.spring_kotlin_rest_api.features.employee.model.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "employee")
data class Employee(
    @Id
    val id: ObjectId = ObjectId.get(),
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role,
    val createdAt: Instant,
)
