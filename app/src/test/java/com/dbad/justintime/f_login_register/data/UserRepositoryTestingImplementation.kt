package com.dbad.justintime.f_login_register.data

import com.dbad.justintime.f_login_register.domain.model.EmergencyContact
import com.dbad.justintime.f_login_register.domain.model.Employee
import com.dbad.justintime.f_login_register.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow

class UserRepositoryTestingImplementation(users: List<User>) : UserRepository {
    private val _usersList = MutableStateFlow(users)
    private val _emergencyContact = MutableStateFlow(listOf(EmergencyContact()))
    private val _employee = MutableStateFlow(listOf(Employee()))

    override suspend fun getUser(user: User): User {
        for (existingUser in _usersList.value) {
            if ((existingUser.email == user.email) && (existingUser.password == user.password)) return existingUser
        }
        return User()
    }

    override suspend fun upsertUser(user: User) {
        if (user.uid == null) {
            val currentUsers = _usersList.value.toMutableList()
            currentUsers.add(
                User(
                    uid = currentUsers.size,
                    email = user.email,
                    password = user.password
                )
            )
            _usersList.value = currentUsers.toList()
        }
    }

    override suspend fun getEmergencyContactKey(emergencyContact: EmergencyContact): Int {
        for (contact in _emergencyContact.value) {
            if ((contact.name == emergencyContact.name) &&
                (contact.email == emergencyContact.email) &&
                (contact.phone == emergencyContact.phone) &&
                (contact.relation == emergencyContact.relation)
            ) {
                return contact.uid!!
            }
        }
        return 0
    }

    override suspend fun upsertEmergencyContact(contact: EmergencyContact) {
        if (contact.uid == null) {
            val currentEmergencyContacts = _emergencyContact.value.toMutableList()
            currentEmergencyContacts.add(
                EmergencyContact(
                    uid = currentEmergencyContacts.size,
                    name = contact.name,
                    preferredName = contact.name,
                    email = contact.email,
                    phone = contact.phone,
                    preferredContactMethod = contact.preferredContactMethod,
                    relation = contact.relation
                )
            )
        }
    }

    override suspend fun getEmployeeKey(employee: Employee): Int {
        for (employ in _employee.value) {
            if ((employee.name == employ.name) &&
                (employee.phone == employ.phone) &&
                (employee.dateOfBirth == employ.dateOfBirth) &&
                (employee.emergencyContact == employ.emergencyContact) &&
                (employee.contractType == employ.contractType) &&
                (employee.manager == employ.manager)
            ) {
                return employ.uid!!
            }
        }
        return 0
    }

    override suspend fun upsertEmployee(employee: Employee) {
        if (employee.uid == null) {
            val currentEmployees = _employee.value.toMutableList()
            currentEmployees.add(
                Employee(
                    uid = currentEmployees.size,
                    name = employee.name,
                    preferredName = employee.preferredName,
                    phone = employee.phone,
                    preferredContactMethod = employee.preferredContactMethod,
                    dateOfBirth = employee.dateOfBirth, //TODO
                    minimumHours = employee.minimumHours,
                    emergencyContact = employee.emergencyContact,
                    contractType = employee.contractType,
                    manager = employee.manager,
                    role = employee.role
                )
            )
        }
    }
}