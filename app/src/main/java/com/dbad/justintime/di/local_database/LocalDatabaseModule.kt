package com.dbad.justintime.di.local_database

import com.dbad.justintime.f_local_db.data.data_source.EventsDB
import com.dbad.justintime.f_local_db.data.data_source.UsersDB
import com.dbad.justintime.f_local_db.domain.repository.LocalDatabaseRepository
import com.dbad.justintime.f_local_db.domain.use_case.LocalDatabaseUseCases

interface LocalDatabaseModule {
    val localUsersDB: UsersDB
    val eventsDB: EventsDB
    val localDatabaseRepo: LocalDatabaseRepository
    val useCases: LocalDatabaseUseCases
}