package com.dbad.justintime.di.login_register

import com.dbad.justintime.f_login_register.data.data_source.UserDatabaseRegisterLogin
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases

interface LoginRegisterModule {
    val dataStore: UserDatabaseRegisterLogin
    val usersRepository: UserRepository
    val useCases: UserUseCases
}