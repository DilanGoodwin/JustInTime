package com.dbad.justintime.f_login_register.data.repository

import com.dbad.justintime.f_login_register.data.data_source.UsersDao
import com.dbad.justintime.f_login_register.domain.model.EmergencyContact
import com.dbad.justintime.f_login_register.domain.model.Employee
import com.dbad.justintime.f_login_register.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository

class UsersRepositoryImplementation(private val dao: UsersDao) : UserRepository {
    override suspend fun getUser(user: User): User {
        return dao.getUser(email = user.email, password = user.password)
    }

    override suspend fun upsertUser(user: User) {
        dao.upsertUser(user = user)
    }

    override suspend fun getEmergencyContactKey(emergencyContact: EmergencyContact): Int {
        return dao.getEmergencyContactKey(
            name = emergencyContact.name,
            email = emergencyContact.email,
            phone = emergencyContact.phone,
            relation = emergencyContact.relation.name
        )
    }

    override suspend fun upsertEmergencyContact(contact: EmergencyContact) {
        dao.upsertEmergencyContact(contact = contact)
    }

    override suspend fun getEmployeeKey(employee: Employee): Int {
        return dao.getEmployeeKey(name = employee.name, phone = employee.phone)
    }

    override suspend fun upsertEmployee(employee: Employee) {
        dao.upsertEmployee(employee = employee)
    }
}