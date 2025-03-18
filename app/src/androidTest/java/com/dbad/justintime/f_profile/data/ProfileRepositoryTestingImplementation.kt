package com.dbad.justintime.f_profile.data

import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.model.User
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository

class ProfileRepositoryTestingImplementation : ProfileRepository {
    override suspend fun getUser(user: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun upsertUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun getEmergencyContact(contact: EmergencyContact): EmergencyContact {
        TODO("Not yet implemented")
    }

    override suspend fun upsertEmergencyContact(contact: EmergencyContact) {
        TODO("Not yet implemented")
    }

    override suspend fun getEmployee(employee: Employee): Employee {
        TODO("Not yet implemented")
    }

    override suspend fun upsertEmployee(employee: Employee) {
        TODO("Not yet implemented")
    }
}