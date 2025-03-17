package com.dbad.justintime.di

import com.dbad.justintime.core.domain.use_case.ValidateDate
import com.dbad.justintime.core.domain.use_case.ValidateEmail
import com.dbad.justintime.core.domain.use_case.ValidatePassword
import com.dbad.justintime.core.domain.use_case.ValidatePhoneNumber
import com.dbad.justintime.f_local_users_db.domain.use_case.LocalDatabaseUseCases
import com.dbad.justintime.f_login_register.data.data_source.UserDatabaseRegisterLogin
import com.dbad.justintime.f_login_register.data.repository.UsersRepositoryImplementation
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import com.dbad.justintime.f_login_register.domain.use_case.GetUser
import com.dbad.justintime.f_login_register.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.f_login_register.domain.use_case.UpsertEmployee
import com.dbad.justintime.f_login_register.domain.use_case.UpsertUser
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases

class LoginRegisterModuleImplementation(localDatabase: LocalDatabaseUseCases) :
    LoginRegisterModule {
    override val dataStore: UserDatabaseRegisterLogin = UserDatabaseRegisterLogin()
    override val usersRepository: UserRepository by lazy {
        UsersRepositoryImplementation(
            localDatabase = localDatabase,
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