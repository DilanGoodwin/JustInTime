package com.dbad.justintime.f_login_register.domain.use_case

import com.dbad.justintime.f_login_register.domain.model.Employee
import com.dbad.justintime.f_login_register.domain.repository.UserRepository

class GetEmployeeKey(private val repository: UserRepository) {
    suspend operator fun invoke(employee: Employee): Int {
        return repository.getEmployeeKey(employee = employee)
    }
}