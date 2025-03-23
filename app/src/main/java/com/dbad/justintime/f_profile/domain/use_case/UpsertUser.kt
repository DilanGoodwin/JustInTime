package com.dbad.justintime.f_profile.domain.use_case

import com.dbad.justintime.f_local_users_db.domain.model.User
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository

class UpsertUser(private val repository: ProfileRepository) {
    suspend operator fun invoke(user: User) {
        repository.upsertUser(user = user)
    }
}