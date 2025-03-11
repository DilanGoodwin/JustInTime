package com.dbad.justintime.f_profile.data.repository

import com.dbad.justintime.core.data.data_source.UsersDao
import com.dbad.justintime.core.domain.model.EmergencyContact
import com.dbad.justintime.core.domain.model.Employee
import com.dbad.justintime.core.domain.model.User
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository

class ProfileRepositoryImplementation(private val dao: UsersDao) : ProfileRepository {

    override suspend fun getUser(user: User): User {
        return dao.getUser(uid = user.uid)
    }

    override suspend fun upsertUser(user: User) {
        dao.upsertUser(user = user)
    }

    override suspend fun getEmergencyContact(contact: EmergencyContact): EmergencyContact {
        TODO("Not yet implemented")
    }

    override suspend fun upsertEmergencyContact(contact: EmergencyContact) {
        dao.upsertEmergencyContact(contact = contact)
    }

    override suspend fun getEmployee(employee: Employee): Employee {
        TODO("Not yet implemented")
    }

    override suspend fun upsertEmployee(employee: Employee) {
        dao.upsertEmployee(employee = employee)
    }
}