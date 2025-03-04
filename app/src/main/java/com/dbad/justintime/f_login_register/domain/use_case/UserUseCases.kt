package com.dbad.justintime.f_login_register.domain.use_case

data class UserUseCases(
    val getUser: GetUser,
    val upsertUser: UpsertUser,
    val getEmployee: GetEmployee,
    val getEmployeeKey: GetEmployeeKey,
    val upsertEmployee: UpsertEmployee,
    val getEmergencyContact: GetEmergencyContact,
    val getEmergencyContactKey: GetEmergencyContactKey,
    val upsertEmergencyContact: UpsertEmergencyContact,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validatePhoneNumber: ValidatePhoneNumber
)
