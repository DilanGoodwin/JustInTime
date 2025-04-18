package com.dbad.justintime.di.shifts

import com.dbad.justintime.f_local_db.domain.use_case.LocalDatabaseUseCases
import com.dbad.justintime.f_shifts.data.data_source.EventDatabase
import com.dbad.justintime.f_shifts.data.repository.ShiftRepositoryImplementation
import com.dbad.justintime.f_shifts.domain.repository.ShiftRepository
import com.dbad.justintime.f_shifts.domain.use_case.GetEmployee
import com.dbad.justintime.f_shifts.domain.use_case.GetEvents
import com.dbad.justintime.f_shifts.domain.use_case.GetPeople
import com.dbad.justintime.f_shifts.domain.use_case.GetUser
import com.dbad.justintime.f_shifts.domain.use_case.ShiftUseCases
import com.dbad.justintime.f_shifts.domain.use_case.UpsertEvent

class ShiftsModuleImplementation(localDatabase: LocalDatabaseUseCases) :
    ShiftsModule {
    override val eventsDatabase: EventDatabase = EventDatabase()
    override val shiftsRepository: ShiftRepository =
        ShiftRepositoryImplementation(
            localDatabase = localDatabase,
            eventsRemoteDatabase = eventsDatabase
        )

    override val useCases: ShiftUseCases = ShiftUseCases(
        getUser = GetUser(repository = shiftsRepository),
        getEmployee = GetEmployee(repository = shiftsRepository),
        getEvents = GetEvents(repo = shiftsRepository),
        upsertEvents = UpsertEvent(repo = shiftsRepository),
        getPeople = GetPeople(repo = shiftsRepository)
    )
}