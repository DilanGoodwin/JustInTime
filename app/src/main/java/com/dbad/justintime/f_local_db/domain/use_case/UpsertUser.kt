package com.dbad.justintime.f_local_db.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_local_db.domain.repository.LocalDatabaseRepository

class UpsertUser(private val repo: LocalDatabaseRepository) {
    suspend operator fun invoke(user: User) {
        repo.upsertUser(user = user)
    }
}