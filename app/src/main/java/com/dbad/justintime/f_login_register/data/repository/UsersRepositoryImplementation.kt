package com.dbad.justintime.f_login_register.data.repository

import com.dbad.justintime.f_login_register.data.data_source.UsersDao
import com.dbad.justintime.f_login_register.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository

class UsersRepositoryImplementation(private val dao: UsersDao) : UserRepository {
    override suspend fun getUser(user: User): User {
        return dao.getUser(user.email, password = user.password)
    }

    override suspend fun upsertUser(user: User) {
        dao.upsertUser(user = user)
    }
}