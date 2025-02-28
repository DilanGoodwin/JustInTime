package com.dbad.justintime.f_login_register.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.domain.util.PasswordErrors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class RegisterViewModel(private val useCases: UserUseCases) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        RegisterState()
    )

    fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.CancelUserRegister -> _state.value.onCancelRegistration()
            RegisterEvent.RegisterUser -> {}
            is RegisterEvent.SetEmail -> {
                _state.update { it.copy(email = event.email) }
                var error = true
                if (useCases.validateEmail(_state.value.email)) error = false
                _state.update { it.copy(showEmailError = error) }
            }

            is RegisterEvent.SetPassword -> {
                _state.update { it.copy(password = event.password) }
                val passwordError = useCases.validatePassword(event.password)
                if (passwordError != PasswordErrors.PASSWORD_NONE) {
                    _state.update { it.copy(passwordErrorCode = passwordError.errorCode) }
                    _state.update { it.copy(showPasswordError = true) }
                }

            }

            is RegisterEvent.SetPasswordMatch -> {
                _state.update { it.copy(passwordMatch = event.password) }
                var passwordError = true
                if (event.password == _state.value.password) passwordError = false
                _state.update { it.copy(showMatchPasswordError = passwordError) }
            }

            is RegisterEvent.SetCancelRegistrationEvent -> _state.update {
                it.copy(onCancelRegistration = event.cancelAction)
            }

            RegisterEvent.ToggleViewPassword -> _state.update { it.copy(showPassword = !(_state.value.showPassword)) }
            RegisterEvent.ToggleViewPasswordMatch -> _state.update { it.copy(showPasswordMatch = !(_state.value.showPasswordMatch)) }
        }
    }
}