package com.dbad.justintime.f_login_register.domain.repository

import com.dbad.justintime.f_login_register.domain.model.EmergencyContact
import com.dbad.justintime.f_login_register.domain.model.Employee
import com.dbad.justintime.f_login_register.domain.model.User

interface UserRepository {
    suspend fun getUser(user: User): User
    suspend fun upsertUser(user: User)

    suspend fun getEmergencyContactKey(emergencyContact: EmergencyContact): Int
    suspend fun upsertEmergencyContact(contact: EmergencyContact)
    
    suspend fun getEmployeeKey(employee: Employee): Int
    suspend fun upsertEmployee(employee: Employee)
}