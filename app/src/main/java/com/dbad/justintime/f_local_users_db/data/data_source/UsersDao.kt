package com.dbad.justintime.f_local_users_db.data.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.model.User

@Dao
interface UsersDao {
    @Query("SELECT * FROM users WHERE uid is :uid")
    suspend fun getUser(uid: String): User

    @Upsert
    suspend fun upsertUser(user: User)

    @Query("DELETE FROM users")
    suspend fun deleteUser()

    @Query("SELECT * FROM employee WHERE uid is :uid")
    suspend fun getEmployee(uid: String): Employee

    @Upsert
    suspend fun upsertEmployee(employee: Employee)

    @Query("DELETE FROM employee")
    suspend fun deleteEmployee()

    @Query("SELECT * FROM emergency_contact WHERE uid is :uid")
    suspend fun getEmergencyContact(uid: String): EmergencyContact

    @Upsert
    suspend fun upsertEmergencyContact(contact: EmergencyContact)

    @Query("DELETE FROM emergency_contact")
    suspend fun deleteEmergencyContact()
}