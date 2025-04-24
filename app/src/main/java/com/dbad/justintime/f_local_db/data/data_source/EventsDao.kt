package com.dbad.justintime.f_local_db.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.Person
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsDao {

    @Query("SELECT * FROM events WHERE type IS :type")
    fun getEvents(type: ShiftEventTypes): Flow<List<Event>>

    @Upsert
    suspend fun upsertEvents(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("DELETE FROM events")
    suspend fun deleteEvents()

    @Query("SELECT * FROM people")
    fun getPeople(): Flow<List<Person>>

    @Upsert
    suspend fun upsertPerson(person: Person)

    @Query("DELETE FROM people")
    suspend fun deletePeople()
}