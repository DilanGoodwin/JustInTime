package com.dbad.justintime.f_login_register.domain.use_case

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
