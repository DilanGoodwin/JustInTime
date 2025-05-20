package com.dbad.justintime.f_login_register.presentation.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.domain.util.PasswordErrors
import com.dbad.justintime.f_user_auth.domain.repository.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val useCases: UserUseCases,
    private val authUser: AuthRepo
) : ViewModel() {
    private val _checkingUser = MutableStateFlow(true)
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
                ) verifyUser()

            }

            is RegisterEvent.SetEmail -> {
                _state.update { it.copy(email = event.email) }
                _checkingUser.update { true }
                showEmailError()
            }

            is RegisterEvent.SetPassword -> {
                _state.update { it.copy(password = event.password) }
                _checkingUser.update { true }
                showPasswordError(password = event.password)
            }

            is RegisterEvent.SetPasswordMatch -> {
                _state.update { it.copy(passwordMatch = event.password) }
                _checkingUser.update { true }
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

    private fun verifyUser() {
        val userUid = User.generateUid(email = _state.value.email)

        viewModelScope.launch {
            val receivedUser = useCases.getUser(
                User(uid = userUid)
            ).first()

            if (receivedUser.uid.isBlank() || receivedUser.employee.isNotBlank()) {
                Log.d(
                    "RegisterViewModel",
                    "The user was not within the remote database, flagging error"
                )
                _state.update { it.copy(showEmailError = true) }
            } else {
                _checkingUser.update { false }
            }
        }

        /*
        We need to wait for the email checks to take place before we continue, this is a delayed
        operation as we are waiting on the remote database which could take some time.
         */
        if (!state.value.showEmailError && !_checkingUser.value) {
            authUser.signUp(email = state.value.email, password = state.value.password)

            viewModelScope.launch {
                val receivedUser: User = useCases.getUser(
                    User(uid = userUid)
                ).first()

                if (!state.value.showEmailError) useCases.upsertUser(
                    User(
                        uid = receivedUser.uid,
                        email = _state.value.email
                    )
                )
            }

            _state.value.onRegistration(userUid)
        }
    }

    companion object {
        fun generateViewModel(
            useCases: UserUseCases,
            authUser: AuthRepo
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RegisterViewModel(
                        useCases = useCases,
                        authUser = authUser
                    ) as T
                }
            }
        }
    }
}