package com.dbad.justintime.f_shifts.data.repository

import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.Person
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes
import com.dbad.justintime.f_local_db.domain.use_case.LocalDatabaseUseCases
import com.dbad.justintime.f_shifts.data.data_source.EventDatabase
import com.dbad.justintime.f_shifts.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.Flow

class ShiftRepositoryImplementation(
    private val localDatabase: LocalDatabaseUseCases,
    private val eventsRemoteDatabase: EventDatabase
) :
    ShiftRepository {

    override suspend fun getUser(user: User): User {
        return localDatabase.getUser(user = user)
    }

    override suspend fun getEmployee(employee: Employee): Employee {
        return localDatabase.getEmployee(employee = employee)
    }

    override fun getEvents(type: ShiftEventTypes): Flow<List<Event>> {
        return localDatabase.getEvents(type = type)
    }

    override suspend fun upsertEvent(event: Event) {
        localDatabase.upsertEvents(event = event)
        eventsRemoteDatabase.upsertEvent(event = event)
    }

    override suspend fun deleteEvent(event: Event) {
        localDatabase.deleteEvent(event = event)
        eventsRemoteDatabase.deleteEvent(event = event)
    }

    override fun getPeople(): Flow<List<Person>> {
        return localDatabase.getPeople()
    }
}