package com.dbad.justintime.f_login_register.data

import com.dbad.justintime.f_login_register.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow

class UserRepositoryTestingImplementation(users: List<User>) : UserRepository {
    private val _usersList = MutableStateFlow(users)

    override suspend fun getUser(user: User): User {
        return findUser(list = _usersList.value.toList(), user = user)
    }

    override suspend fun upsertUser(user: User) {
        if (user.uid == null) {
            val currentList = _usersList.value.toMutableList()
            currentList.add(
                User(
                    uid = currentList.toList().size,
                    email = user.email,
                    password = user.password
                )
            )
            _usersList.value = currentList.toList()
        }
    }

    private fun findUser(list: List<User>, user: User): User {
        for (details in list) {
            if ((details.email == user.email) && (details.password == user.password)) return details
        }
        return User()
    }
}