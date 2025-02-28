package com.dbad.justintime.di

import android.content.Context
import com.dbad.justintime.f_login_register.data.data_source.UsersDB
import com.dbad.justintime.f_login_register.data.repository.UsersRepositoryImplementation
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import com.dbad.justintime.f_login_register.domain.use_case.GetUser
import com.dbad.justintime.f_login_register.domain.use_case.UpsertUser
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.domain.use_case.ValidateEmail
import com.dbad.justintime.f_login_register.domain.use_case.ValidatePassword

class LoginRegisterModuleImplementation(context: Context) : LoginRegisterModule {
    override val usersDB: UsersDB = UsersDB.getInstance(context = context)
    override val usersRepository: UserRepository by lazy { UsersRepositoryImplementation(usersDB.dao) }
    override val useCases: UserUseCases by lazy {
        UserUseCases(
            getUser = GetUser(repository = usersRepository),
            upsertUser = UpsertUser(repository = usersRepository),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
        )
    }
}