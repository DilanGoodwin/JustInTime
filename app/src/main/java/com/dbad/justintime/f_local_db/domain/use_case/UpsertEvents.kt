package com.dbad.justintime.f_local_db.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.repository.LocalDatabaseRepository

class UpsertEvents(private val repo: LocalDatabaseRepository) {
    suspend operator fun invoke(event: Event) {
        repo.upsertEvent(event = event)
    }
}