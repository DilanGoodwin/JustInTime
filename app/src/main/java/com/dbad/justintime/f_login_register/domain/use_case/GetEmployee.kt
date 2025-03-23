package com.dbad.justintime.f_login_register.domain.use_case

import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetEmployee(private val repository: UserRepository) {
    suspend operator fun invoke(employee: Employee): Flow<Employee> {
        return repository.getEmployee(employee = employee)
    }
}