package com.dbad.justintime.f_login_register.data.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dbad.justintime.f_login_register.domain.model.User

@Dao
interface UsersDao {
    @Query("SELECT * FROM users WHERE email is :email and password is :password")
    suspend fun getUser(email: String, password: String): User

    @Upsert
    suspend fun upsertUser(user: User)
}