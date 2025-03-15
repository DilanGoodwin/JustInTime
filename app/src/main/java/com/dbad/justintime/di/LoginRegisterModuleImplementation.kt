package com.dbad.justintime.di

import android.content.Context
import com.dbad.justintime.core.data.data_source.UsersDB
import com.dbad.justintime.core.domain.use_case.GetUser
import com.dbad.justintime.core.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.core.domain.use_case.UpsertEmployee
import com.dbad.justintime.core.domain.use_case.UpsertUser
import com.dbad.justintime.core.domain.use_case.ValidateDate
import com.dbad.justintime.core.domain.use_case.ValidateEmail
import com.dbad.justintime.core.domain.use_case.ValidatePassword
import com.dbad.justintime.core.domain.use_case.ValidatePhoneNumber
import com.dbad.justintime.f_login_register.data.data_source.UserDatabaseRegisterLogin
import com.dbad.justintime.f_login_register.data.repository.UsersRepositoryImplementation
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import com.dbad.justintime.f_login_register.domain.user_case.UserUseCases

class LoginRegisterModuleImplementation(context: Context) :
    LoginRegisterModule {
    override val dataStore: UserDatabaseRegisterLogin = UserDatabaseRegisterLogin()
    override val usersDB: UsersDB = UsersDB.getInstance(context = context)
    override val usersRepository: UserRepository by lazy {
        UsersRepositoryImplementation(
            dao = usersDB.dao,
            dataStore = dataStore
        )
    }
    override val useCases: UserUseCases by lazy {
        UserUseCases(
            getUser = GetUser(repository = usersRepository),
            upsertUser = UpsertUser(repository = usersRepository),
            upsertEmployee = UpsertEmployee(repository = usersRepository),
            upsertEmergencyContact = UpsertEmergencyContact(repository = usersRepository),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validateDate = ValidateDate()
        )
    }
}