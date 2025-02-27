package com.dbad.justintime.f_login_register.presentation.register

sealed interface RegisterEvent {
    data class SetEmail(val email: String) : RegisterEvent
    data class SetPassword(val password: String) : RegisterEvent
    data class SetPasswordMatch(val password: String) : RegisterEvent
    data object CancelUserRegister : RegisterEvent
    data object RegisterUser : RegisterEvent
    data object ToggleViewPassword : RegisterEvent
    data object ToggleViewPasswordMatch : RegisterEvent
}