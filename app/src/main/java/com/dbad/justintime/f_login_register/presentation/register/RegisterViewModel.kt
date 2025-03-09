package com.dbad.justintime.f_login_register.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbad.justintime.core.domain.model.User
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.domain.util.PasswordErrors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

class RegisterViewModel(private val useCases: UserUseCases) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        RegisterState()
    )

    fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.CancelUserRegister -> _state.value.onCancelRegistration()
            RegisterEvent.RegisterUser -> {
                showEmailError()
                showPasswordError(password = _state.value.password)
                showMatchPasswordError(password = _state.value.passwordMatch)

                if (!(_state.value.showEmailError ||
                            _state.value.showPasswordError ||
                            _state.value.showMatchPasswordError)
                ) runBlocking { verifyUser() }

            }

            is RegisterEvent.SetEmail -> {
                _state.update { it.copy(email = event.email) }
                showEmailError()
            }

            is RegisterEvent.SetPassword -> {
                _state.update { it.copy(password = event.password) }
                showPasswordError(password = event.password)
            }

            is RegisterEvent.SetPasswordMatch -> {
                _state.update { it.copy(passwordMatch = event.password) }
                showMatchPasswordError(event.password)
            }

            is RegisterEvent.SetCancelRegistrationEvent -> _state.update {
                it.copy(onCancelRegistration = event.cancelAction)
            }

            is RegisterEvent.SetRegistrationEvent -> _state.update {
                it.copy(onRegistration = event.registerAction)
            }

            RegisterEvent.ToggleViewPassword -> _state.update { it.copy(showPassword = !(_state.value.showPassword)) }
            RegisterEvent.ToggleViewPasswordMatch -> _state.update { it.copy(showPasswordMatch = !(_state.value.showPasswordMatch)) }
        }
    }

    private fun showEmailError() {
        var error = true
        if (useCases.validateEmail(_state.value.email)) error = false
        _state.update { it.copy(showEmailError = error) }
    }

    private fun showPasswordError(password: String) {
        val passwordError = useCases.validatePassword(password = password)
        if (passwordError != PasswordErrors.PASSWORD_NONE) {
            _state.update { it.copy(passwordErrorCode = passwordError.errorCode) }
            _state.update { it.copy(showPasswordError = true) }
        } else {
            _state.update { it.copy(showPasswordError = false) }
        }
    }

    private fun showMatchPasswordError(password: String) {
        var passwordError = true
        if (password == _state.value.password) passwordError = false
        _state.update { it.copy(showMatchPasswordError = passwordError) }
    }

    private suspend fun verifyUser() {
        val receivedUser = useCases.getUser(
            User(email = _state.value.email)
        ).first()

        try {
            if (receivedUser.uid.isBlank() || receivedUser.password.isNotBlank()) {
                _state.update { it.copy(showEmailError = true) }
                return
            }
        } catch (e: NullPointerException) {
            _state.update { it.copy(showEmailError = true) }
            return
        }


        useCases.upsertUser(
            User(
                uid = receivedUser.uid,
                email = _state.value.email,
                password = _state.value.password
            )
        )

        _state.value.onRegistration(receivedUser.uid)
    }
}