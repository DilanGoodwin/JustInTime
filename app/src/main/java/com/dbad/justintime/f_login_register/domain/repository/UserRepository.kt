package com.dbad.justintime.f_login_register.domain.repository

import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(user: User): Flow<User>
    suspend fun upsertUser(user: User)

    suspend fun upsertEmergencyContact(contact: EmergencyContact)
    suspend fun upsertEmployee(employee: Employee)
}