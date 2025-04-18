package com.dbad.justintime.f_login_register.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository

class UpdateLocalDatabase(private val repository: UserRepository) {
    suspend operator fun invoke(
        user: User,
        employee: Employee,
        emergencyContact: EmergencyContact
    ) {
        repository.updateLocalDatabase(
            user = user,
            employee = employee,
            emergencyContact = emergencyContact
        )
    }
}