package com.dbad.justintime.f_login_register.presentation.user_details

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class UserDetailsViewModel(private val useCases: UserUseCases) : ViewModel() {
    private val _state = MutableStateFlow(UserDetailsState())
    val state = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        UserDetailsState()
    )

    fun onEvent(event: UserDetailsEvents) {
        when (event) {
            is UserDetailsEvents.SetRegisterEvent -> _state.update { it.copy(registerEvent = event.registerAction) }
            is UserDetailsEvents.SetCancelEvent -> _state.update { it.copy(cancelEvent = event.cancelAction) }

            // User Input Setters
            is UserDetailsEvents.SetName -> if (event.name.firstOrNull { it.isDigit() } == null) _state.update {
                it.copy(name = event.name)
            }

            is UserDetailsEvents.SetPrefName -> if (event.name.firstOrNull { it.isDigit() } == null) _state.update {
                it.copy(preferredName = event.name)
            }

            is UserDetailsEvents.SetPhoneNumb -> {
                if (event.phone.isDigitsOnly()) _state.update { it.copy(phoneNumber = event.phone) }
                showPhoneError()
            }

            is UserDetailsEvents.SetPrefContactMethod -> _state.update {
                it.copy(prefContactMethod = event.contactMethod)
            }

            // Emergency Contact Area Setters
            is UserDetailsEvents.SetEmergencyContactName -> {
                if (event.name.firstOrNull { it.isDigit() } == null) _state.update {
                    it.copy(emergencyContactName = event.name)
                }
            }

            is UserDetailsEvents.SetEmergencyContactPrefName -> {
                if (event.name.firstOrNull { it.isDigit() } == null) _state.update {
                    it.copy(emergencyContactPrefName = event.name)
                }
            }

            is UserDetailsEvents.SetEmergencyContactPhoneNumb -> {
                if (event.phone.isDigitsOnly()) _state.update {
                    it.copy(emergencyContactPhone = event.phone)
                }
                showEmergencyPhoneError()
            }

            is UserDetailsEvents.SetEmergencyContactEmail -> {
                _state.update {
                    it.copy(emergencyContactEmail = event.email)
                }
                showEmailError()
            }

            is UserDetailsEvents.SetEmergencyContactPrefContactMethod -> _state.update {
                it.copy(emergencyContactPrefContactMethod = event.contactMethod)
            }

            // Toggles
            UserDetailsEvents.TogglePrefContactDropDown -> _state.update {
                it.copy(prefContDropDownExpanded = !_state.value.prefContDropDownExpanded)
            }

            UserDetailsEvents.ToggleEmergencyContactArea -> _state.update {
                it.copy(emergencyContactAreaExpanded = !_state.value.emergencyContactAreaExpanded)
            }

            UserDetailsEvents.ToggleEmergencyContactPrefContactDropDown -> _state.update {
                it.copy(emergencyContactPrefContDropDownExpand = !_state.value.emergencyContactPrefContDropDownExpand)
            }

            // Buttons
            UserDetailsEvents.CancelEvent -> _state.value.cancelEvent()
            UserDetailsEvents.RegisterEvent -> {
                if (_state.value.name.isBlank()) _state.update { it.copy(showNameFieldError = true) }
                if (_state.value.emergencyContactName.isBlank()) _state.update {
                    it.copy(showEmergencyContactNameError = true)
                }
                showPhoneError()
                showEmailError()
                showEmergencyPhoneError()
            }
        }
    }

    private fun showEmailError() {
        var error = true
        if (useCases.validateEmail(_state.value.emergencyContactEmail)) {
            error = false
        } else {
            _state.update { it.copy(emergencyContactAreaExpanded = true) }
        }
        _state.update { it.copy(showEmergencyContactEmailError = error) }
    }

    private fun showPhoneError() {
        var error = true
        if (useCases.validatePhoneNumber(_state.value.phoneNumber)) error = false
        _state.update { it.copy(showPhoneNumbFieldError = error) }
    }

    private fun showEmergencyPhoneError() {
        var error = true
        if (useCases.validatePhoneNumber(_state.value.emergencyContactPhone)) {
            error = false
        } else {
            _state.update { it.copy(emergencyContactAreaExpanded = true) }
        }
        _state.update { it.copy(showEmergencyContactPhoneError = error) }
    }
}