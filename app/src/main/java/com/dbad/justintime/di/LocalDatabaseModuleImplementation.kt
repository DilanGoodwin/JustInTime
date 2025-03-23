package com.dbad.justintime.di

import android.content.Context
import com.dbad.justintime.f_local_users_db.data.data_source.UsersDB
import com.dbad.justintime.f_local_users_db.data.repository.LocalUsersRepositoryImplementation
import com.dbad.justintime.f_local_users_db.domain.repository.LocalUsersRepository
import com.dbad.justintime.f_local_users_db.domain.use_case.ClearLocalDatabase
import com.dbad.justintime.f_local_users_db.domain.use_case.GetEmergencyContact
import com.dbad.justintime.f_local_users_db.domain.use_case.GetEmployee
import com.dbad.justintime.f_local_users_db.domain.use_case.GetUser
import com.dbad.justintime.f_local_users_db.domain.use_case.LocalDatabaseUseCases
import com.dbad.justintime.f_local_users_db.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.f_local_users_db.domain.use_case.UpsertEmployee
import com.dbad.justintime.f_local_users_db.domain.use_case.UpsertUser

class LocalDatabaseModuleImplementation(context: Context) : LocalDatabaseModule {
    override val localUsersDB: UsersDB = UsersDB.getInstance(context = context)
    override val localDatabaseRepo: LocalUsersRepository by lazy {
        LocalUsersRepositoryImplementation(dao = localUsersDB.dao)
    }
    override val useCases: LocalDatabaseUseCases by lazy {
        LocalDatabaseUseCases(
            getUser = GetUser(repo = localDatabaseRepo),
            upsertUser = UpsertUser(repo = localDatabaseRepo),
            getEmployee = GetEmployee(repo = localDatabaseRepo),
            upsertEmployee = UpsertEmployee(repo = localDatabaseRepo),
            getEmergencyContact = GetEmergencyContact(repo = localDatabaseRepo),
            upsertEmergencyContact = UpsertEmergencyContact(repo = localDatabaseRepo),
            clearLocalDatabase = ClearLocalDatabase(repo = localDatabaseRepo)
        )
    }
}