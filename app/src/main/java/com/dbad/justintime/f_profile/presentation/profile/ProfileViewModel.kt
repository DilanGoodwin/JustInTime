package com.dbad.justintime.f_profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbad.justintime.core.domain.model.Employee
import com.dbad.justintime.core.domain.model.User
import com.dbad.justintime.core.domain.model.util.PreferredContactMethod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ProfileViewModel() : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        ProfileState()
    )

    fun onUserEvent(event: ProfileUserEvents) {
        when (event) {
            is ProfileUserEvents.SetName -> updateEmployee(name = event.name)
            is ProfileUserEvents.SetPreferredName -> updateEmployee(preferredName = event.name)
            is ProfileUserEvents.SetEmail -> updateUser(email = event.email)
            is ProfileUserEvents.SetDatOfBirth -> updateEmployee(dateOfBirth = event.dateOfBirth)
            is ProfileUserEvents.SetPhoneNumb -> updateEmployee(phone = event.phone)
            is ProfileUserEvents.SetPrefContactMethod -> updateEmployee(prefContactMethod = event.contactMethod)

            ProfileUserEvents.ToggleExpandedArea -> _state.update {
                it.copy(expandUserInformationArea = !_state.value.expandUserInformationArea)
            }

            ProfileUserEvents.TogglePrefContactDropDown -> _state.update {
                it.copy(expandPrefContactMethod = !_state.value.expandPrefContactMethod)
            }

            ProfileUserEvents.ToggleShowDatePicker -> _state.update {
                it.copy(showDateOfBirthPicker = !_state.value.showDateOfBirthPicker)
            }
        }
    }

    /**
     * Updates the Users details that can be edited on the Profile screen.
     * The uid for the user will be recalculated to ensure that if the email is changed the user
     * is still able to login.
     * Update changed status to True
     *
     * @param email Value to change the email to, default value is current state held value.
     * @param password Value to change the password to, default value is current state held value.
     * Passed value must be hashed otherwise value will be updated to plaintext
     */
    private fun updateUser(
        email: String = _state.value.user.email,
        password: String = _state.value.user.password
    ) {
        _state.update {
            it.copy(
                user = User(
                    uid = User.generateUid(email = email),
                    email = email,
                    password = password,
                    employee = _state.value.user.employee
                ),
                changeMade = true
            )
        }
    }

    /**
     * Updates Employee details that can be edited on the profile screen.
     * Update changed status to True
     *
     * @param name Value to change the name to, default value is current state held value
     * @param preferredName Value to change the preferred name to, default is current state held
     * value
     * @param phone Value to change the phone to, default is current state held value
     * @param prefContactMethod Value to change the preferred contact method to, default is current
     * state held value
     * @param dateOfBirth Value to change the date of birth to, default is current state held value
     */
    private fun updateEmployee(
        name: String = _state.value.employee.name,
        preferredName: String = _state.value.employee.preferredName,
        phone: String = _state.value.employee.phone,
        prefContactMethod: PreferredContactMethod = _state.value.employee.preferredContactMethod,
        dateOfBirth: String = _state.value.employee.dateOfBirth
    ) {
        _state.update {
            it.copy(
                employee = Employee(
                    uid = _state.value.employee.uid,
                    name = name,
                    preferredName = preferredName,
                    phone = phone,
                    preferredContactMethod = prefContactMethod,
                    dateOfBirth = dateOfBirth,
                    minimumHours = _state.value.employee.minimumHours,
                    emergencyContact = _state.value.employee.emergencyContact,
                    isAdmin = _state.value.employee.isAdmin,
                    companyName = _state.value.employee.companyName,
                    contractType = _state.value.employee.contractType,
                    manager = _state.value.employee.manager,
                    role = _state.value.employee.role
                ),
                changeMade = true
            )
        }
    }


}