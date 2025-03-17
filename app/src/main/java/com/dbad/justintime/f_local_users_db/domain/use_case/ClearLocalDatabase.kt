package com.dbad.justintime.f_local_users_db.domain.use_case

import com.dbad.justintime.f_local_users_db.domain.repository.LocalUsersRepository

class ClearLocalDatabase(private val repo: LocalUsersRepository) {
    suspend operator fun invoke() {
        repo.clearLocalDatabase()
    }
}