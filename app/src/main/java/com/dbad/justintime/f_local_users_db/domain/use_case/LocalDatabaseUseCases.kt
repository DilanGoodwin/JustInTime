package com.dbad.justintime.f_local_users_db.domain.use_case

data class LocalDatabaseUseCases(
    val getUser: GetUser,
    val upsertUser: UpsertUser,
    val getEmployee: GetEmployee,
    val upsertEmployee: UpsertEmployee,
    val getEmergencyContact: GetEmergencyContact,
    val upsertEmergencyContact: UpsertEmergencyContact,
    val clearLocalDatabase: ClearLocalDatabase
)