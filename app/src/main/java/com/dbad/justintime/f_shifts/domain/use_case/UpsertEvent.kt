package com.dbad.justintime.f_shifts.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_shifts.domain.repository.ShiftRepository

class UpsertEvent(private val repo: ShiftRepository) {
    suspend operator fun invoke(event: Event) {
        repo.upsertEvent(event = event)
    }
}