package com.dbad.justintime.f_shifts.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_shifts.domain.repository.ShiftRepository

class GetUser(private val repository: ShiftRepository) {
    suspend operator fun invoke(user: User): User {
        return repository.getUser(user = user)
    }
}