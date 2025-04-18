package com.dbad.justintime.f_shifts.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes
import com.dbad.justintime.f_shifts.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.Flow

class GetEvents(private val repo: ShiftRepository) {
    operator fun invoke(
        type: ShiftEventTypes,
        userOnlyEvents: Boolean,
        userId: String
    ): Flow<List<Event>> {
        return repo.getEvents(type = type, userOnlyEvents = userOnlyEvents, userId = userId)
    }
}