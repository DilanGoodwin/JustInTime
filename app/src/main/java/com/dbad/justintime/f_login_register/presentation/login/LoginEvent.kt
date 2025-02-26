package com.dbad.justintime.f_login_register.presentation.login

interface LoginEvent {
    data class SetEmail(val email: String) : LoginEvent
    data class SetPassword(val password: String) : LoginEvent
    data object LoginUser : LoginEvent
    data object RegisterUser : LoginEvent
    object ToggleViewPassword : LoginEvent
}