package com.dbad.justintime.f_login_register.data

import com.dbad.justintime.f_local_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository

// Empty creation instance of ProfileRepository for testing login functions
class ProfileRepositoryTestingImplementation() : ProfileRepository {

    override suspend fun getUser(user: User): User {
        return User()
    }

    override suspend fun upsertUser(user: User) {}

    override suspend fun getEmergencyContact(contact: EmergencyContact): EmergencyContact {
        return EmergencyContact()
    }

    override suspend fun upsertEmergencyContact(contact: EmergencyContact) {}

    override suspend fun getEmployee(employee: Employee): Employee {
        return Employee()
    }

    override suspend fun upsertEmployee(employee: Employee) {}
}