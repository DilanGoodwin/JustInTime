package com.dbad.justintime.f_login_register.presentation.login

sealed interface LoginEvent {
    data class SetEmail(val email: String) : LoginEvent
    data class SetPassword(val password: String) : LoginEvent
    data object LoginUser : LoginEvent
    data object RegisterUser : LoginEvent
    data object ToggleViewPassword : LoginEvent
}