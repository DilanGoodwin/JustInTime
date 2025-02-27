package com.dbad.justintime.f_login_register.presentation.register

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val passwordMatch: String = "",
    val showPassword: Boolean = false,
    val showPasswordMatch: Boolean = false
)