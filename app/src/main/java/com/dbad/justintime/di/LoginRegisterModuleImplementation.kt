package com.dbad.justintime.di

import android.content.Context
import com.dbad.justintime.f_login_register.data.data_source.UsersDB
import com.dbad.justintime.f_login_register.data.repository.UsersRepositoryImplementation
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import com.dbad.justintime.f_login_register.domain.use_case.GetEmergencyContact
import com.dbad.justintime.f_login_register.domain.use_case.GetEmergencyContactKey
import com.dbad.justintime.f_login_register.domain.use_case.GetEmployee
import com.dbad.justintime.f_login_register.domain.use_case.GetEmployeeKey
import com.dbad.justintime.f_login_register.domain.use_case.GetUser
import com.dbad.justintime.f_login_register.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.f_login_register.domain.use_case.UpsertEmployee
import com.dbad.justintime.f_login_register.domain.use_case.UpsertUser
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.domain.use_case.ValidateEmail
import com.dbad.justintime.f_login_register.domain.use_case.ValidatePassword
import com.dbad.justintime.f_login_register.domain.use_case.ValidatePhoneNumber

class LoginRegisterModuleImplementation(context: Context) : LoginRegisterModule {
    override val usersDB: UsersDB = UsersDB.getInstance(context = context)
    override val usersRepository: UserRepository by lazy { UsersRepositoryImplementation(usersDB.dao) }
    override val useCases: UserUseCases by lazy {
        UserUseCases(
            getUser = GetUser(repository = usersRepository),
            upsertUser = UpsertUser(repository = usersRepository),
            getEmployee = GetEmployee(repository = usersRepository),
            getEmployeeKey = GetEmployeeKey(repository = usersRepository),
            upsertEmployee = UpsertEmployee(repository = usersRepository),
            getEmergencyContact = GetEmergencyContact(repository = usersRepository),
            getEmergencyContactKey = GetEmergencyContactKey(repository = usersRepository),
            upsertEmergencyContact = UpsertEmergencyContact(repository = usersRepository),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validatePhoneNumber = ValidatePhoneNumber()
        )
    }
}