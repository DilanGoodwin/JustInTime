package com.dbad.justintime.f_local_users_db.domain.use_case

import com.dbad.justintime.f_local_users_db.domain.model.User
import com.dbad.justintime.f_local_users_db.domain.repository.LocalUsersRepository

class GetUser(private val repo: LocalUsersRepository) {
    suspend operator fun invoke(user: User): User {
        return repo.getUser(user = user)
    }
}