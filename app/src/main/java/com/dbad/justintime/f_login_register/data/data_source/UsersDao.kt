package com.dbad.justintime.f_login_register.data.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dbad.justintime.f_login_register.domain.model.EmergencyContact
import com.dbad.justintime.f_login_register.domain.model.Employee
import com.dbad.justintime.f_login_register.domain.model.User

@Dao
interface UsersDao {
    @Query("SELECT * FROM users WHERE email is :email and password is :password")
    suspend fun getUser(email: String, password: String): User

    @Upsert
    suspend fun upsertUser(user: User)

    @Query("SELECT * FROM emergency_contact WHERE name is :name and email is :email and phone is :phone and relation is :relation")
    suspend fun getEmergencyContact(
        name: String,
        email: String,
        phone: String,
        relation: String
    ): EmergencyContact

    @Query("SELECT uid FROM emergency_contact WHERE name is :name and email is :email and phone is :phone and relation is :relation")
    suspend fun getEmergencyContactKey(
        name: String,
        email: String,
        phone: String,
        relation: String
    ): Int

    @Upsert
    suspend fun upsertEmergencyContact(contact: EmergencyContact)

    @Query("SELECT * FROM employee WHERE name is :name and phone is :phone")
    suspend fun getEmployee(name: String, phone: String): Employee

    @Query("SELECT uid FROM employee WHERE name is :name and phone is :phone")
    suspend fun getEmployeeKey(name: String, phone: String): Int

    @Upsert
    suspend fun upsertEmployee(employee: Employee)
}