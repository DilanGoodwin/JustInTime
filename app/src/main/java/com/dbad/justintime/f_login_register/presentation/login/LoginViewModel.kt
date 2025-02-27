package com.dbad.justintime.f_login_register.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        LoginState()
    )

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.SetEmail -> _state.update { it.copy(email = event.email) }
            is LoginEvent.SetPassword -> _state.update { it.copy(password = event.password) }
            is LoginEvent.ToggleViewPassword -> {
                _state.update { it.copy(showPassword = !(_state.value.showPassword)) }
            }

            LoginEvent.LoginUser -> {}
            LoginEvent.RegisterUser -> {}
        }
    }
}