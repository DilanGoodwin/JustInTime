package com.dbad.justintime.core.domain.use_case

import com.dbad.justintime.core.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUser(private val repository: UserRepository) {
    operator fun invoke(user: User): Flow<User> {
        return repository.getUser(user = user)
    }
}