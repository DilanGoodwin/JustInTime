package com.dbad.justintime.f_login_register.domain.use_case

import com.dbad.justintime.core.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository

class GetUser(private val repository: UserRepository) {
    suspend operator fun invoke(user: User): User {
        return repository.getUser(user = user)
    }
}