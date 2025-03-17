package com.dbad.justintime.f_login_register.data.repository

import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.model.User
import com.dbad.justintime.f_local_users_db.domain.use_case.LocalDatabaseUseCases
import com.dbad.justintime.f_login_register.data.data_source.UserDatabaseRegisterLogin
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UsersRepositoryImplementation(
    private val localDatabase: LocalDatabaseUseCases,
    private val dataStore: UserDatabaseRegisterLogin
) : UserRepository {

    override fun getUser(user: User): Flow<User> {
        return dataStore.getUser(user = user)
    }

    override suspend fun upsertUser(user: User) {
        dataStore.upsertUser(user = user)
        localDatabase.upsertUser(user = user)
    }

    override suspend fun upsertEmergencyContact(contact: EmergencyContact) {
        dataStore.upsertEmergencyContact(emergencyContact = contact)
        localDatabase.upsertEmergencyContact(emergencyContact = contact)
    }

    override suspend fun upsertEmployee(employee: Employee) {
        dataStore.upsertEmployee(employee = employee)
        localDatabase.upsertEmployee(employee = employee)
    }
}