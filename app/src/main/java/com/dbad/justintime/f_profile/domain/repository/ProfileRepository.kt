package com.dbad.justintime.f_profile.domain.repository

import com.dbad.justintime.f_local_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUser(user: User): User
    suspend fun upsertUser(user: User)

    suspend fun getEmergencyContact(contact: EmergencyContact): EmergencyContact
    suspend fun upsertEmergencyContact(contact: EmergencyContact)

    suspend fun getEmployee(employee: Employee): Employee
    suspend fun upsertEmployee(employee: Employee)

    fun checkUserNotExist(user: User): Flow<User>
    suspend fun addNewUser(user: User)

    suspend fun clearDatabase()
}