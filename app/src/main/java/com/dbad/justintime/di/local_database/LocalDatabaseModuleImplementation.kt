package com.dbad.justintime.di.local_database

import android.content.Context
import com.dbad.justintime.f_local_db.data.data_source.EventsDB
import com.dbad.justintime.f_local_db.data.data_source.UsersDB
import com.dbad.justintime.f_local_db.data.repository.LocalDatabaseRepositoryImplementation
import com.dbad.justintime.f_local_db.domain.repository.LocalDatabaseRepository
import com.dbad.justintime.f_local_db.domain.use_case.ClearLocalDatabase
import com.dbad.justintime.f_local_db.domain.use_case.GetCurrentUserEventsOnly
import com.dbad.justintime.f_local_db.domain.use_case.GetEmergencyContact
import com.dbad.justintime.f_local_db.domain.use_case.GetEmployee
import com.dbad.justintime.f_local_db.domain.use_case.GetEvents
import com.dbad.justintime.f_local_db.domain.use_case.GetPeople
import com.dbad.justintime.f_local_db.domain.use_case.GetUser
import com.dbad.justintime.f_local_db.domain.use_case.LocalDatabaseUseCases
import com.dbad.justintime.f_local_db.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.f_local_db.domain.use_case.UpsertEmployee
import com.dbad.justintime.f_local_db.domain.use_case.UpsertEvents
import com.dbad.justintime.f_local_db.domain.use_case.UpsertPeople
import com.dbad.justintime.f_local_db.domain.use_case.UpsertUser

class LocalDatabaseModuleImplementation(context: Context) : LocalDatabaseModule {
    override val localUsersDB: UsersDB = UsersDB.getInstance(context = context)
    override val eventsDB: EventsDB = EventsDB.getInstance(context = context)
    override val localDatabaseRepo: LocalDatabaseRepository by lazy {
        LocalDatabaseRepositoryImplementation(eventsDao = eventsDB.dao, usersDao = localUsersDB.dao)
    }
    override val useCases: LocalDatabaseUseCases by lazy {
        LocalDatabaseUseCases(
            getUser = GetUser(repo = localDatabaseRepo),
            upsertUser = UpsertUser(repo = localDatabaseRepo),
            getEmployee = GetEmployee(repo = localDatabaseRepo),
            upsertEmployee = UpsertEmployee(repo = localDatabaseRepo),
            getEmergencyContact = GetEmergencyContact(repo = localDatabaseRepo),
            upsertEmergencyContact = UpsertEmergencyContact(repo = localDatabaseRepo),
            getEvents = GetEvents(repo = localDatabaseRepo),
            getCurrentUserEvents = GetCurrentUserEventsOnly(repo = localDatabaseRepo),
            upsertEvents = UpsertEvents(repo = localDatabaseRepo),
            getPeople = GetPeople(repo = localDatabaseRepo),
            upsertPeople = UpsertPeople(repo = localDatabaseRepo),
            clearLocalDatabase = ClearLocalDatabase(repo = localDatabaseRepo)
        )
    }
}