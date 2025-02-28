package com.dbad.justintime.f_login_register.domain.use_case

import com.dbad.justintime.f_login_register.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository

class UpsertUser(private val repository: UserRepository) {
    suspend operator fun invoke(user: User) {
        repository.upsertUser(user = user)
    }
}