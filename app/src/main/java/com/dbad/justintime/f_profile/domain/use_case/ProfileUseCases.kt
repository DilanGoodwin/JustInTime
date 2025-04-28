package com.dbad.justintime.f_profile.domain.use_case

import com.dbad.justintime.core.domain.use_case.ValidateDate
import com.dbad.justintime.core.domain.use_case.ValidateEmail
import com.dbad.justintime.core.domain.use_case.ValidatePassword
import com.dbad.justintime.core.domain.use_case.ValidatePhoneNumber

data class ProfileUseCases(
    val getUser: GetUser,
    val upsertUser: UpsertUser,
    val getEmployee: GetEmployee,
    val upsertEmployee: UpsertEmployee,
    val getEmergencyContact: GetEmergencyContact,
    val upsertEmergencyContact: UpsertEmergencyContact,
    val checkUserNotExist: CheckUserNotExist,
    val addNewUser: AddNewUser,
    val clearDatabase: ClearDatabase,

    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validatePhoneNumber: ValidatePhoneNumber,
    val validateDate: ValidateDate
)