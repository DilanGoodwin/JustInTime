package com.dbad.justintime.f_login_register.presentation.user_details

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbad.justintime.f_login_register.domain.model.EmergencyContact
import com.dbad.justintime.f_login_register.domain.model.Employee
import com.dbad.justintime.f_login_register.domain.model.User
import com.dbad.justintime.f_login_register.domain.model.util.PreferredContactMethod
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.presentation.util.DATE_FORMATTER
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import java.util.Date

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

            is UserDetailsEvents.SetDateOfBirth -> {
                _state.update { it.copy(userDateOfBirth = event.dateOfBirth) }
                _state.update { it.copy(showDatePicker = !_state.value.showDatePicker) }
                showDateErrors()
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
                showEmergencyEmailError()
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

            UserDetailsEvents.ToggleDatePicker -> _state.update {
                it.copy(showDatePicker = !_state.value.showDatePicker)
            }

            // Buttons
            UserDetailsEvents.CancelEvent -> _state.value.cancelEvent()
            UserDetailsEvents.RegisterEvent -> {
                if (_state.value.name.isBlank()) _state.update { it.copy(showNameFieldError = true) }
                if (_state.value.emergencyContactName.isBlank()) _state.update {
                    it.copy(showEmergencyContactNameFieldError = true)
                }
                showPhoneError()
                showDateErrors()
                showEmergencyEmailError()
                showEmergencyPhoneError()

                if (!(_state.value.showNameFieldError ||
                            _state.value.showDatePickerError ||
                            _state.value.showEmergencyContactNameFieldError ||
                            _state.value.showPhoneNumbFieldError ||
                            _state.value.showEmergencyContactPhoneError ||
                            _state.value.showEmergencyContactEmailError)
                ) runBlocking { createUser() }
            }

            is UserDetailsEvents.SetEmail -> _state.update { it.copy(email = event.email) }
            is UserDetailsEvents.SetPassword -> _state.update { it.copy(password = event.password) }
        }
    }

    private fun showDateErrors() {
        var error = true
        if (useCases.validateDate(
                currentDate = DATE_FORMATTER.format(Date()),
                dateToCheck = _state.value.userDateOfBirth
            )
        ) error = false
        _state.update { it.copy(showDatePickerError = error) }
    }

    private fun showPhoneError() {
        var error = true
        if (useCases.validatePhoneNumber(_state.value.phoneNumber)) error = false
        _state.update { it.copy(showPhoneNumbFieldError = error) }
    }

    private fun showEmergencyEmailError() {
        var error = true
        if (useCases.validateEmail(_state.value.emergencyContactEmail)) {
            error = false
        } else {
            _state.update { it.copy(emergencyContactAreaExpanded = true) }
        }
        _state.update { it.copy(showEmergencyContactEmailError = error) }
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

    private suspend fun createUser(): Int {
        // Create emergency contact
        val emergencyContact = EmergencyContact(
            name = _state.value.emergencyContactName,
            preferredName = _state.value.emergencyContactPrefName,
            email = _state.value.emergencyContactEmail,
            phone = _state.value.emergencyContactPhone,
            preferredContactMethod = if (_state.value.emergencyContactPrefContactMethod != PreferredContactMethod.NONE) {
                _state.value.emergencyContactPrefContactMethod
            } else {
                PreferredContactMethod.PHONE
            }
            // relation
        )

        useCases.upsertEmergencyContact(emergencyContact = emergencyContact)

        val employee = Employee(
            name = _state.value.name,
            preferredName = _state.value.preferredName,
            phone = _state.value.phoneNumber,
            preferredContactMethod = if (_state.value.prefContactMethod != PreferredContactMethod.NONE) {
                _state.value.prefContactMethod
            } else {
                PreferredContactMethod.PHONE
            },
            dateOfBirth = _state.value.userDateOfBirth,
            emergencyContact = useCases.getEmergencyContactKey(emergencyContact = emergencyContact)
        )

        useCases.upsertEmployee(employee = employee)
        val employeeKey = useCases.getEmployeeKey(employee = employee)

        val user = User(
            email = _state.value.email,
            password = _state.value.password,
            employee = employeeKey
        )

        useCases.upsertUser(user = user)
        return employeeKey
    }
}