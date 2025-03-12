package com.dbad.justintime.f_login_register.data.repository

import com.dbad.justintime.core.data.data_source.UsersDao
import com.dbad.justintime.core.domain.model.EmergencyContact
import com.dbad.justintime.core.domain.model.Employee
import com.dbad.justintime.core.domain.model.User
import com.dbad.justintime.f_login_register.data.data_source.UserDatabaseRegisterLogin
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UsersRepositoryImplementation(
    private val dao: UsersDao,
    private val dataStore: UserDatabaseRegisterLogin
) : UserRepository {

    override fun getUser(user: User): Flow<User> {
        return dataStore.getUser(user = user)
    }

    override suspend fun upsertUser(user: User) {
        dataStore.upsertUser(user = user)
        dao.upsertUser(user = user)
    }

    override suspend fun upsertEmergencyContact(contact: EmergencyContact) {
        dataStore.upsertEmergencyContact(emergencyContact = contact)
        dao.upsertEmergencyContact(contact = contact)
    }

    override suspend fun upsertEmployee(employee: Employee) {
        dataStore.upsertEmployee(employee = employee)
        dao.upsertEmployee(employee = employee)
    }
}