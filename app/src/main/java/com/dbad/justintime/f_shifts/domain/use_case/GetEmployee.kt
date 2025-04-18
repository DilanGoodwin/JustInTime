package com.dbad.justintime.f_shifts.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_shifts.domain.repository.ShiftRepository

class GetEmployee(private val repository: ShiftRepository) {
    suspend operator fun invoke(employee: Employee): Employee {
        return repository.getEmployee(employee = employee)
    }
}