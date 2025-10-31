package com.example.spring_kotlin_rest_api.features.employee.mapper

import com.example.spring_kotlin_rest_api.features.employee.model.dto.EmployeeDto
import com.example.spring_kotlin_rest_api.features.employee.model.entity.Employee
import org.bson.types.ObjectId
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface EmployeeMapper {
    companion object {
        val INSTANCE: EmployeeMapper = Mappers.getMapper(EmployeeMapper::class.java)
    }

    fun toDto(employee: Employee): EmployeeDto
    fun toEmployee(employee: EmployeeDto): Employee

    fun mapObjectIdToString(objectId: ObjectId?): String? {
        return objectId?.toHexString()
    }

    fun mapStringToObjectId(id: String?): ObjectId? {
        return if (id != null) ObjectId(id) else null
    }
}
