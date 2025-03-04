package com.dbad.justintime.f_login_register.data

import com.dbad.justintime.f_login_register.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow

class UsersRepositoryTestingImplementation(users: List<User>) : UserRepository {
    private val _usersList = MutableStateFlow(users)

    override suspend fun getUser(user: User): User {
        for (existingUser in _usersList.value) {
            if ((existingUser.email == user.email) && (existingUser.password == user.password)) return existingUser
        }
        return User()
    }

    override suspend fun upsertUser(user: User) {
        if (user.uid == null) {
            val currentUsers = _usersList.value.toMutableList()
            currentUsers.add(
                User(
                    uid = currentUsers.size,
                    email = user.email,
                    password = user.password
                )
            )
            _usersList.value = currentUsers.toList()
        }
    }
}