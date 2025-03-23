package com.dbad.justintime.di.local_database

import com.dbad.justintime.f_local_users_db.data.data_source.UsersDB
import com.dbad.justintime.f_local_users_db.domain.repository.LocalUsersRepository
import com.dbad.justintime.f_local_users_db.domain.use_case.LocalDatabaseUseCases

interface LocalDatabaseModule {
    val localUsersDB: UsersDB
    val localDatabaseRepo: LocalUsersRepository
    val useCases: LocalDatabaseUseCases
}