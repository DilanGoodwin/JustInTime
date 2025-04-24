package com.dbad.justintime.f_local_db.domain.use_case

import com.dbad.justintime.f_local_db.domain.repository.LocalDatabaseRepository

class ClearLocalDatabase(private val repo: LocalDatabaseRepository) {
    suspend operator fun invoke() {
        repo.clearLocalDatabase()
    }
}