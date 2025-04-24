package com.dbad.justintime.f_local_db.domain.use_case

data class LocalDatabaseUseCases(
    val getUser: GetUser,
    val upsertUser: UpsertUser,
    val getEmployee: GetEmployee,
    val upsertEmployee: UpsertEmployee,
    val getEmergencyContact: GetEmergencyContact,
    val upsertEmergencyContact: UpsertEmergencyContact,
    val getEvents: GetEvents,
    val upsertEvents: UpsertEvents,
    val deleteEvent: DeleteEvent,
    val getPeople: GetPeople,
    val upsertPeople: UpsertPeople,
    val clearLocalDatabase: ClearLocalDatabase
)