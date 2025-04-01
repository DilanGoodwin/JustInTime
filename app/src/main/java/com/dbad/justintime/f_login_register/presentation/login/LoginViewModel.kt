package com.dbad.justintime.f_login_register.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.model.User
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.domain.util.PasswordErrors
import com.dbad.justintime.f_user_auth.data.data_source.UserAuthConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val useCases: UserUseCases,
    private val authUser: UserAuthConnection
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = LoginState()
    )

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.SetEmail -> _state.update { it.copy(email = event.email) }
            is LoginEvent.SetPassword -> _state.update { it.copy(password = event.password) }
            is LoginEvent.ToggleViewPassword -> _state.update { it.copy(showPassword = !(_state.value.showPassword)) }
            is LoginEvent.SetRegistrationAction -> _state.update { it.copy(onRegistration = event.registrationAction) }
            is LoginEvent.SetLoginAction -> _state.update { it.copy(onLogin = event.loginAction) }

            LoginEvent.LoginUser -> loginUser()
            LoginEvent.RegisterUser -> {
                _state.value.onRegistration()
            }
        }
    }

    private fun loginUser() {
        var error = false
        if (!useCases.validateEmail(_state.value.email)) error = true
        if (useCases.validatePassword(_state.value.password) != PasswordErrors.PASSWORD_NONE) {
            error = true
        }
        _state.update { it.copy(showError = error) }

        if (!error) {
            val tmpUser = User(
                uid = User.generateUid(email = _state.value.email),
                email = _state.value.email
            )

            // login user
            authUser.loginUser(
                email = _state.value.email,
                password = _state.value.password
            )

            if (authUser.authState.value!!) {
                viewModelScope.launch {
                    // Get the user info
                    val receivedUser = useCases.getUser(user = tmpUser).first()

                    // Grab the employee & emergency contact information
                    val employeeInfo =
                        useCases.getEmployee(employee = Employee(uid = receivedUser.employee))
                            .first()

                    if (employeeInfo.uid.isBlank()) {
                        _state.update { it.copy(showError = true) }
                    } else {
                        val emergencyContactInfo =
                            useCases.getEmergencyContact(emergencyContact = EmergencyContact(uid = employeeInfo.emergencyContact))
                                .first()

                        if (emergencyContactInfo.uid.isBlank()) {
                            _state.update { it.copy(showError = true) }
                        } else {
                            // Add details to local database
                            useCases.updateLocalDatabase(
                                user = receivedUser,
                                employee = employeeInfo,
                                emergencyContact = emergencyContactInfo
                            )
                        }
                    }
                }
            }
            if (!_state.value.showError && (authUser.authState.value != false)) {
                _state.value.onLogin()
            } else {
                _state.update { it.copy(showError = true) }
            }
        }
    }

    companion object {
        fun generateViewModel(
            useCases: UserUseCases,
            authUser: UserAuthConnection
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return LoginViewModel(
                        useCases = useCases,
                        authUser = authUser
                    ) as T
                }
            }
        }
    }
}