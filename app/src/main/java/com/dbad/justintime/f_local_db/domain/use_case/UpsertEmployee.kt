package com.dbad.justintime.f_local_db.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.repository.LocalDatabaseRepository

class UpsertEmployee(private val repo: LocalDatabaseRepository) {
    suspend operator fun invoke(employee: Employee) {
        repo.upsertEmployee(employee = employee)
    }
}