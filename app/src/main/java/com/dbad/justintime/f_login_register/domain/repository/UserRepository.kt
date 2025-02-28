package com.dbad.justintime.f_login_register.domain.repository

import com.dbad.justintime.f_login_register.domain.model.User

interface UserRepository {
    suspend fun getUser(user: User): User
    suspend fun upsertUser(user: User)
}