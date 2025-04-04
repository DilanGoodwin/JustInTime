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
        localDatabase.upsertUser(user = user)
        dataStore.upsertUser(user = user)
    }

    override suspend fun getEmployee(employee: Employee): Flow<Employee> {
        return dataStore.getEmployee(employee = employee)
    }

    override suspend fun upsertEmployee(employee: Employee) {
        localDatabase.upsertEmployee(employee = employee)
        dataStore.upsertEmployee(employee = employee)
    }

    override suspend fun getEmergencyContact(emergencyContact: EmergencyContact): Flow<EmergencyContact> {
        return dataStore.getEmergencyContact(emergencyContact = emergencyContact)
    }

    override suspend fun upsertEmergencyContact(contact: EmergencyContact) {
        localDatabase.upsertEmergencyContact(emergencyContact = contact)
        dataStore.upsertEmergencyContact(emergencyContact = contact)
    }

    override suspend fun updateLocalDatabase(
        user: User,
        employee: Employee,
        emergencyContact: EmergencyContact
    ) {
        localDatabase.upsertUser(user = user)
        localDatabase.upsertEmployee(employee = employee)
        localDatabase.upsertEmergencyContact(emergencyContact = emergencyContact)
    }
}
