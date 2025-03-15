package com.dbad.justintime.f_login_register.domain.user_case

import com.dbad.justintime.core.domain.use_case.GetUser
import com.dbad.justintime.core.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.core.domain.use_case.UpsertEmployee
import com.dbad.justintime.core.domain.use_case.UpsertUser
import com.dbad.justintime.core.domain.use_case.ValidateDate
import com.dbad.justintime.core.domain.use_case.ValidateEmail
import com.dbad.justintime.core.domain.use_case.ValidatePassword
import com.dbad.justintime.core.domain.use_case.ValidatePhoneNumber

data class UserUseCases(
    val getUser: GetUser,
    val upsertUser: UpsertUser,
    val upsertEmployee: UpsertEmployee,
    val upsertEmergencyContact: UpsertEmergencyContact,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validatePhoneNumber: ValidatePhoneNumber,
    val validateDate: ValidateDate
)