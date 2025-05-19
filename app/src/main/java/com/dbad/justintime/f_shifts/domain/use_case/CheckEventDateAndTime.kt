package com.dbad.justintime.f_shifts.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_shifts.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.Flow

class CheckEventDateAndTime(private val repo: ShiftRepository) {
    operator fun invoke(event: Event, person: String): Flow<Boolean> {
        return repo.checkEventDateAndTime(event = event, person = person)
    }
}