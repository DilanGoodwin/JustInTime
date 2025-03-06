package com.dbad.justintime.f_login_register.presentation.register

data class RegisterState(
    val email: String = "",
    val showEmailError: Boolean = false,
    val password: String = "",
    val showPassword: Boolean = false,
    val showPasswordError: Boolean = false,
    val passwordErrorCode: Int = 0,
    val passwordMatch: String = "",
    val showPasswordMatch: Boolean = false,
    val showMatchPasswordError: Boolean = false,
    val onCancelRegistration: () -> Unit = {},
    val onRegistration: (Int) -> Unit = {}
)