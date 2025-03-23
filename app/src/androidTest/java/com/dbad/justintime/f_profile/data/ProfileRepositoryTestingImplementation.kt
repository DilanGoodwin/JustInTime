package com.dbad.justintime.f_profile.data

import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.model.User
import com.dbad.justintime.f_local_users_db.domain.model.util.ContractType
import com.dbad.justintime.f_local_users_db.domain.model.util.Relation
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow

class ProfileRepositoryTestingImplementation(
    users: List<User>,
    employees: List<Employee>,
    emergencyContacts: List<EmergencyContact>
) : ProfileRepository {
    private val _usersList = MutableStateFlow(users)
    private val _employee = MutableStateFlow(employees)
    private val _emergencyContact = MutableStateFlow(emergencyContacts)

    override suspend fun getUser(user: User): User {
        for (existingUser in _usersList.value) {
            if (existingUser.uid == user.uid) {
                return existingUser
            }
        }
        return User()
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

    override suspend fun getEmployee(employee: Employee): Employee {
        for (existingEmployee in _employee.value) {
            if (existingEmployee.uid == employee.uid) {
                return existingEmployee
            }
        }
        return Employee()
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

    override suspend fun getEmergencyContact(emergencyContact: EmergencyContact): EmergencyContact {
        for (existingEmergencyContact in _emergencyContact.value) {
            if (existingEmergencyContact.uid == emergencyContact.uid) {
                return existingEmergencyContact
            }
        }
        return EmergencyContact()
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

    companion object {
        val userUid = User.generateUid(email = "cassandra.negrete@justintime.com")
        private val employeeUid = Employee.generateUid(
            userUid = userUid,
            name = "Cassandra Negrete",
            phone = "07573881133"
        )
        private val emergencyContactUid = EmergencyContact.generateUid(
            email = "jessica.bland@justintime.com",
            employeeUid = employeeUid
        )

        // Create an EmergencyContact
        private val emergencyContacts: List<EmergencyContact> = listOf(
            EmergencyContact(
                uid = emergencyContactUid,
                name = "Jessica Bland",
                email = "jessica.bland@justintime.com",
                phone = "07154979316",
                relation = Relation.SIGNIFICANT_OTHER
            )
        )

        // Create an Employee
        private val employees: List<Employee> = listOf(
            Employee(
                uid = employeeUid,
                name = "Cassandra Negrete",
                phone = "07573881133",
                dateOfBirth = "29/03/1974",
                emergencyContact = emergencyContactUid,
                isAdmin = true,
                companyName = "Testing Company .Co",
                contractType = ContractType.SALARY,
                manager = "Paul Smith",
                role = "Tester"
            )
        )

        // Create a User
        private val users: List<User> = listOf(
            User(
                uid = userUid,
                email = "cassandra.negrete@justintime.com",
                password = User.hashPassword(password = "C@55@ndr@P4ssword"),
                employee = employeeUid
            )
        )

        fun generateProfileTestRepo(): ProfileRepository {
            return ProfileRepositoryTestingImplementation(
                users = users,
                employees = employees,
                emergencyContacts = emergencyContacts
            )
        }
    }
}