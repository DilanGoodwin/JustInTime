package com.dbad.justintime.f_login_register.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val showError: Boolean = false,
    val onRegistration: () -> Unit = {},
    val onLogin: (String) -> Unit = {}
)
