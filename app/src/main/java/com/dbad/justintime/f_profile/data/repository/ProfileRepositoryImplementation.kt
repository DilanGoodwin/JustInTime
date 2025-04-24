package com.dbad.justintime.f_profile.data.repository

import com.dbad.justintime.f_local_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_local_db.domain.use_case.LocalDatabaseUseCases
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository

class ProfileRepositoryImplementation(private val localDatabase: LocalDatabaseUseCases) :
    ProfileRepository {

    override suspend fun getUser(user: User): User {
        return localDatabase.getUser(user = user)
    }

    override suspend fun upsertUser(user: User) {
        localDatabase.upsertUser(user = user)
    }

    override suspend fun getEmergencyContact(contact: EmergencyContact): EmergencyContact {
        return localDatabase.getEmergencyContact(emergencyContact = contact)
    }

    override suspend fun upsertEmergencyContact(contact: EmergencyContact) {
        localDatabase.upsertEmergencyContact(emergencyContact = contact)
    }

    override suspend fun getEmployee(employee: Employee): Employee {
        return localDatabase.getEmployee(employee = employee)
    }

    override suspend fun upsertEmployee(employee: Employee) {
        localDatabase.upsertEmployee(employee = employee)
    }

    override suspend fun clearDatabase() {
        localDatabase.clearLocalDatabase()
    }
}