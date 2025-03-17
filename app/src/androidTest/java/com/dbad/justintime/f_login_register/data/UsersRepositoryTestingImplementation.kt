package com.dbad.justintime.f_login_register.data

import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class UsersRepositoryTestingImplementation(users: List<User>) : UserRepository {
    private val _usersList = MutableStateFlow(users)
    private val _emergencyContact = MutableStateFlow(listOf(EmergencyContact()))
    private val _employee = MutableStateFlow(listOf(Employee()))

    override fun getUser(user: User): Flow<User> {
        for (existingUser in _usersList.value) {
            if (existingUser.uid == user.uid) {
                return flowOf(existingUser)
            }
        }
        return flowOf(User())
    }

    override suspend fun upsertUser(user: User) {
        for (currentUser in _usersList.value) {
            if (currentUser.uid == user.uid) {
                return
            }
        }
        val currentUsers = _usersList.value.toMutableList()
        currentUsers.add(
            User(
                uid = user.uid,
                email = user.email,
                password = user.password
            )
        )
        _usersList.value = currentUsers.toList()
    }

    override suspend fun upsertEmergencyContact(contact: EmergencyContact) {
        for (existingEmergencyContact in _emergencyContact.value) {
            if ((existingEmergencyContact.uid == contact.uid) && (existingEmergencyContact.email == contact.email)) {
                return
            }
        }

        val currentEmergencyContacts = _emergencyContact.value.toMutableList()
        currentEmergencyContacts.add(
            EmergencyContact(
                uid = contact.uid,
                name = contact.name,
                preferredName = contact.name,
                email = contact.email,
                phone = contact.phone,
                preferredContactMethod = contact.preferredContactMethod,
                relation = contact.relation
            )
        )
    }

    override suspend fun upsertEmployee(employee: Employee) {
        for (existingEmployee in _employee.value) {
            if ((existingEmployee.uid == employee.uid) && (existingEmployee.name == employee.name)) {
                return
            }
        }

        val currentEmployees = _employee.value.toMutableList()
        currentEmployees.add(
            Employee(
                uid = employee.uid,
                name = employee.name,
                preferredName = employee.preferredName,
                phone = employee.phone,
                preferredContactMethod = employee.preferredContactMethod,
                dateOfBirth = employee.dateOfBirth,
                minimumHours = employee.minimumHours,
                emergencyContact = employee.emergencyContact,
                contractType = employee.contractType,
                manager = employee.manager,
                role = employee.role
            )
        )
    }
}