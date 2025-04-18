package com.dbad.justintime.f_shifts.domain.repository

import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.Person
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes
import kotlinx.coroutines.flow.Flow

interface ShiftRepository {
    suspend fun getUser(user: User): User
    suspend fun getEmployee(employee: Employee): Employee

    fun getEvents(
        type: ShiftEventTypes,
        userOnlyEvents: Boolean,
        userId: String
    ): Flow<List<Event>>

    suspend fun upsertEvent(event: Event)

    fun getPeople(): Flow<List<Person>>
}