package com.dbad.justintime.f_login_register.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbad.justintime.core.domain.model.User
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.domain.util.PasswordErrors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val useCases: UserUseCases) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        LoginState()
    )

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.SetEmail -> _state.update { it.copy(email = event.email) }
            is LoginEvent.SetPassword -> _state.update { it.copy(password = event.password) }
            is LoginEvent.ToggleViewPassword -> _state.update { it.copy(showPassword = !(_state.value.showPassword)) }
            is LoginEvent.SetRegistrationAction -> _state.update { it.copy(onRegistration = event.registrationAction) }
            is LoginEvent.SetLoginAction -> _state.update { it.copy(onLogin = event.loginAction) }

            LoginEvent.LoginUser -> {
                var error = false
                if (!useCases.validateEmail(_state.value.email)) error = true
                if (useCases.validatePassword(_state.value.password) != PasswordErrors.PASSWORD_NONE) {
                    error = true
                }
                _state.update { it.copy(showError = error) }

                if (!error) runBlocking { checkUserExists() }
            }

            LoginEvent.RegisterUser -> {
                _state.value.onRegistration()
            }
        }
    }

    private suspend fun checkUserExists() {
        val tmpUser = User(
            email = _state.value.email,
            password = _state.value.password
        )

        val receivedUser = useCases.getUser(user = tmpUser)
        if (receivedUser.uid == null) {
            _state.update { it.copy(showError = true) }
        } else {
            _state.value.onLogin(receivedUser.uid)
        }
    }
}