package com.dbad.justintime.f_local_users_db.data.repository

import com.dbad.justintime.f_local_users_db.data.data_source.UsersDao
import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.model.User
import com.dbad.justintime.f_local_users_db.domain.repository.LocalUsersRepository

class LocalUsersRepositoryImplementation(private val dao: UsersDao) : LocalUsersRepository {
    override suspend fun getUser(user: User): User {
        return dao.getUser(uid = user.uid)
    }

    override suspend fun upsertUser(user: User) {
        dao.upsertUser(user = user)
    }

    override suspend fun getEmployee(employee: Employee): Employee {
        return dao.getEmployee(uid = employee.uid)
    }

    override suspend fun upsertEmployee(employee: Employee) {
        dao.upsertEmployee(employee = employee)
    }

    override suspend fun getEmergencyContact(emergencyContact: EmergencyContact): EmergencyContact {
        return dao.getEmergencyContact(uid = emergencyContact.uid)
    }

    override suspend fun upsertEmergencyContact(emergencyContact: EmergencyContact) {
        dao.upsertEmergencyContact(contact = emergencyContact)
    }

    override suspend fun clearLocalDatabase() {
        dao.deleteEmergencyContact()
        dao.deleteEmployee()
        dao.deleteUser()
    }
}