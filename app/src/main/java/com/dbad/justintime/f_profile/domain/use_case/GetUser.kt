package com.dbad.justintime.f_profile.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository

class GetUser(private val repository: ProfileRepository) {
    suspend operator fun invoke(user: User): User {
        return repository.getUser(user = user)
    }
}