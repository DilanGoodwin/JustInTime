package com.dbad.justintime.f_local_users_db.domain.repository

import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.model.User

interface LocalUsersRepository {
    suspend fun getUser(user: User): User
    suspend fun upsertUser(user: User)

    suspend fun getEmployee(employee: Employee): Employee
    suspend fun upsertEmployee(employee: Employee)

    suspend fun getEmergencyContact(emergencyContact: EmergencyContact): EmergencyContact
    suspend fun upsertEmergencyContact(emergencyContact: EmergencyContact)

    suspend fun clearLocalDatabase()
}