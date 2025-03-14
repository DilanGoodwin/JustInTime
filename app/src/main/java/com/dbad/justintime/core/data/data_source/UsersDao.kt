package com.dbad.justintime.core.data.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dbad.justintime.core.domain.model.EmergencyContact
import com.dbad.justintime.core.domain.model.Employee
import com.dbad.justintime.core.domain.model.User

@Dao
interface UsersDao {
    @Query("SELECT * FROM users WHERE uid is :uid")
    suspend fun getUser(uid: String = ""): User

    @Upsert
    suspend fun upsertUser(user: User)

    @Upsert
    suspend fun upsertEmergencyContact(contact: EmergencyContact)

    @Upsert
    suspend fun upsertEmployee(employee: Employee)
}