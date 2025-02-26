package com.dbad.justintime.f_login_register.presentation.login

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false
)
