package com.dbad.justintime.f_local_users_db.domain.use_case

import com.dbad.justintime.f_local_users_db.domain.model.User
import com.dbad.justintime.f_local_users_db.domain.repository.LocalUsersRepository

class UpsertUser(private val repo: LocalUsersRepository) {
    suspend operator fun invoke(user: User) {
        repo.upsertUser(user = user)
    }
}