package com.dbad.justintime.f_login_register.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        RegisterState()
    )

    fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.CancelUserRegister -> {}
            RegisterEvent.RegisterUser -> {}
            is RegisterEvent.SetEmail -> _state.update { it.copy(email = event.email) }
            is RegisterEvent.SetPassword -> _state.update { it.copy(password = event.password) }
            is RegisterEvent.SetPasswordMatch -> _state.update { it.copy(passwordMatch = event.password) }
            RegisterEvent.ToggleViewPassword -> _state.update { it.copy(showPassword = !(_state.value.showPassword)) }
            RegisterEvent.ToggleViewPasswordMatch -> _state.update { it.copy(showPasswordMatch = !(_state.value.showPasswordMatch)) }
        }
    }
}