package com.dbad.justintime.f_login_register.domain.repository

import com.dbad.justintime.core.domain.model.EmergencyContact
import com.dbad.justintime.core.domain.model.Employee
import com.dbad.justintime.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(user: User): Flow<User>
    suspend fun upsertUser(user: User)

    suspend fun getEmergencyContactKey(emergencyContact: EmergencyContact): Int
    suspend fun upsertEmergencyContact(contact: EmergencyContact)

    suspend fun getEmployeeKey(employee: Employee): Int
    suspend fun upsertEmployee(employee: Employee)
}