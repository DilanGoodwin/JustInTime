package com.dbad.justintime.f_local_db.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.repository.LocalDatabaseRepository

class GetEmployee(private val repo: LocalDatabaseRepository) {
    suspend operator fun invoke(employee: Employee): Employee {
        return repo.getEmployee(employee = employee)
    }
}