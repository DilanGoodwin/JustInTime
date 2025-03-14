package com.dbad.justintime.f_profile.domain.repository

import com.dbad.justintime.core.domain.model.EmergencyContact
import com.dbad.justintime.core.domain.model.Employee
import com.dbad.justintime.core.domain.model.User

interface ProfileRepository {
    suspend fun getUser(user: User): User
    suspend fun upsertUser(user: User)

    suspend fun getEmergencyContact(contact: EmergencyContact): EmergencyContact
    suspend fun upsertEmergencyContact(contact: EmergencyContact)

    suspend fun getEmployee(employee: Employee): Employee
    suspend fun upsertEmployee(employee: Employee)
}