package com.dbad.justintime.f_profile.domain.use_case

import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository

class UpsertEmployee(private val repository: ProfileRepository) {
    suspend operator fun invoke(employee: Employee) {
        repository.upsertEmployee(employee = employee)
    }
}