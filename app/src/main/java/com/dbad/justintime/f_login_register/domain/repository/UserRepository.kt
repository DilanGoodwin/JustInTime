package com.dbad.justintime.f_login_register.domain.repository

import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(user: User): Flow<User>
    suspend fun upsertUser(user: User)

    suspend fun getEmployee(employee: Employee): Flow<Employee>
    suspend fun upsertEmployee(employee: Employee)

    suspend fun getEmergencyContact(emergencyContact: EmergencyContact): Flow<EmergencyContact>
    suspend fun upsertEmergencyContact(contact: EmergencyContact)

    suspend fun updateLocalDatabase(
        user: User,
        employee: Employee,
        emergencyContact: EmergencyContact
    )
}