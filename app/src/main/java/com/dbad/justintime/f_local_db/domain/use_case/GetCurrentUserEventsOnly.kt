package com.dbad.justintime.f_local_db.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes
import com.dbad.justintime.f_local_db.domain.repository.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentUserEventsOnly(private val repo: LocalDatabaseRepository) {
    operator fun invoke(
        employeeUid: String,
        type: ShiftEventTypes
    ): Flow<List<Event>> {
        return repo.getEvents(type = type)
    }
}