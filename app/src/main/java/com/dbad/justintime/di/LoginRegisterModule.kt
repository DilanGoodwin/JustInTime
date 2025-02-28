package com.dbad.justintime.di

import com.dbad.justintime.f_login_register.data.data_source.UsersDB
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases

interface LoginRegisterModule {
    val usersDB: UsersDB
    val usersRepository: UserRepository
    val useCases: UserUseCases
}