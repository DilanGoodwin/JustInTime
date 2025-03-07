package com.dbad.justintime.f_login_register.data

import com.dbad.justintime.core.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import com.dbad.justintime.f_login_register.domain.use_case.GetEmergencyContactKey
import com.dbad.justintime.f_login_register.domain.use_case.GetEmployeeKey
import com.dbad.justintime.f_login_register.domain.use_case.GetUser
import com.dbad.justintime.f_login_register.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.f_login_register.domain.use_case.UpsertEmployee
import com.dbad.justintime.f_login_register.domain.use_case.UpsertUser
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.domain.use_case.ValidateDate
import com.dbad.justintime.f_login_register.domain.use_case.ValidateEmail
import com.dbad.justintime.f_login_register.domain.use_case.ValidatePassword
import com.dbad.justintime.f_login_register.domain.use_case.ValidatePhoneNumber

fun generateUseCase(users: List<User>): UserUseCases {
    val userRepo: UserRepository = UsersRepositoryTestingImplementation(users = users)
    val useCases = UserUseCases(
        getUser = GetUser(repository = userRepo),
        upsertUser = UpsertUser(repository = userRepo),
        getEmployeeKey = GetEmployeeKey(repository = userRepo),
        upsertEmployee = UpsertEmployee(repository = userRepo),
        getEmergencyContactKey = GetEmergencyContactKey(repository = userRepo),
        upsertEmergencyContact = UpsertEmergencyContact(repository = userRepo),
        validateEmail = ValidateEmail(),
        validatePassword = ValidatePassword(),
        validatePhoneNumber = ValidatePhoneNumber(),
        validateDate = ValidateDate()
    )
    return useCases
}