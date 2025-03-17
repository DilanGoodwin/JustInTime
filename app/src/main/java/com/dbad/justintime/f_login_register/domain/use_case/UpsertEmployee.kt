package com.dbad.justintime.f_login_register.domain.use_case

import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_login_register.domain.repository.UserRepository

class UpsertEmployee(private val repository: UserRepository) {
    suspend operator fun invoke(employee: Employee) {
        repository.upsertEmployee(employee = employee)
    }
}