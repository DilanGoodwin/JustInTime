package com.dbad.justintime.f_local_users_db.domain.use_case

import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.repository.LocalUsersRepository

class GetEmployee(private val repo: LocalUsersRepository) {
    suspend operator fun invoke(employee: Employee): Employee {
        return repo.getEmployee(employee = employee)
    }
}