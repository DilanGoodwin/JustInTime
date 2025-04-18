package com.dbad.justintime.f_profile.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository

class GetEmployee(private val repository: ProfileRepository) {
    suspend operator fun invoke(employee: Employee): Employee {
        return repository.getEmployee(employee = employee)
    }
}