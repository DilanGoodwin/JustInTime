package com.dbad.justintime.f_local_db.domain.repository

import com.dbad.justintime.f_local_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.Person
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes
import kotlinx.coroutines.flow.Flow

interface LocalDatabaseRepository {
    suspend fun getUser(user: User): User
    suspend fun upsertUser(user: User)

    suspend fun getEmployee(employee: Employee): Employee
    suspend fun upsertEmployee(employee: Employee)

    suspend fun getEmergencyContact(emergencyContact: EmergencyContact): EmergencyContact
    suspend fun upsertEmergencyContact(emergencyContact: EmergencyContact)

    fun getEvents(type: ShiftEventTypes): Flow<List<Event>>
    suspend fun upsertEvent(event: Event)

    fun getPeople(): Flow<List<Person>>
    suspend fun upsertPerson(person: Person)

    suspend fun clearLocalDatabase()
}