package com.dbad.justintime.f_login_register.domain.use_case

import com.dbad.justintime.f_login_register.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository

class GetUser(private val repository: UserRepository) {
    suspend fun invoke(user: User) {
        repository.getUser(user = user)
    }
}