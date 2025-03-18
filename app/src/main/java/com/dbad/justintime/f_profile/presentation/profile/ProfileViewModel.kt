package com.dbad.justintime.f_profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dbad.justintime.f_local_datastore.domain.repository.UserPreferencesRepository
import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.model.User
import com.dbad.justintime.f_local_users_db.domain.model.util.PreferredContactMethod
import com.dbad.justintime.f_login_register.domain.util.PasswordErrors
import com.dbad.justintime.f_profile.domain.use_case.ProfileUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val useCases: ProfileUseCases,
    private val preferencesDataStore: UserPreferencesRepository
) : ViewModel() {

    // ViewModel State
    private val _state = MutableStateFlow(ProfileState())
    private val _loadingData = MutableStateFlow(false)

    // User, Employee & EmergencyContact State
    private val _user = MutableStateFlow(User())
    private val _employee = MutableStateFlow(Employee())
    private val _emergencyContact = MutableStateFlow(EmergencyContact())

    val state = combine(_state, _user, _employee, _emergencyContact)
    { state, user, employee, emergencyContact ->
        state.copy(
            user = user,
            employee = employee,
            emergencyContact = emergencyContact
        )
    }.onStart { loadInitialData() }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        ProfileState()
    )

    /**
     * Helper function pulling data from the local database into the ViewModel on start
     */
    fun loadInitialData() {
        viewModelScope.launch {
            _loadingData.value = true

            delay(timeMillis = 5000L) //TODO need a better way of working out how long to wait before loading data

            val loginToken = preferencesDataStore.tokenFlow.first()
            _user.value = useCases.getUser(user = User(uid = loginToken))
            _employee.value = useCases.getEmployee(employee = Employee(uid = _user.value.employee))
            _emergencyContact.value =
                useCases.getEmergencyContact(emergencyContact = EmergencyContact(uid = _employee.value.emergencyContact))

            _loadingData.value = false
        }
    }

    fun onUserEvent(event: ProfileUserEvents) {
        when (event) {
            is ProfileUserEvents.SetName -> updateEmployee(name = event.name)
            is ProfileUserEvents.SetPreferredName -> updateEmployee(preferredName = event.name)
            is ProfileUserEvents.SetEmail -> updateUser(email = event.email)
            is ProfileUserEvents.SetDatOfBirth -> updateEmployee(dateOfBirth = event.dateOfBirth)
            is ProfileUserEvents.SetPhoneNumb -> updateEmployee(phone = event.phone)
            is ProfileUserEvents.SetPrefContactMethod -> updateEmployee(prefContactMethod = event.contactMethod)

            ProfileUserEvents.ToggleExpandedArea ->
                toggleExpandableAreas(userInformation = !_state.value.expandUserInformationArea)

            ProfileUserEvents.TogglePrefContactDropDown -> _state.update {
                it.copy(expandPrefContactMethod = !_state.value.expandPrefContactMethod)
            }

            ProfileUserEvents.ToggleShowDatePicker -> _state.update {
                it.copy(showDateOfBirthPicker = !_state.value.showDateOfBirthPicker)
            }
        }
    }

    fun onPasswordEvent(event: ProfilePasswordEvents) {
        when (event) {
            is ProfilePasswordEvents.OldPasswordInput -> _state.update { it.copy(oldPassword = event.oldPassword) }
            is ProfilePasswordEvents.NewPasswordInput -> {
                // If the password does not meet standards then raise the appropriate error to the
                // user
                val passwordError = useCases.validatePassword(password = event.newPassword)
                val showError = passwordError != PasswordErrors.PASSWORD_NONE
                _state.update {
                    it.copy(
                        newPassword = event.newPassword,
                        newPasswordErrorString = passwordError,
                        newPasswordShowError = showError
                    )
                }
            }

            is ProfilePasswordEvents.NewPasswordMatchInput -> {
                // If the passwords do not match then flag an error to the user
                val showError = _state.value.newPassword != event.newPassword
                _state.update {
                    it.copy(
                        newMatchPassword = event.newPassword,
                        newMatchPasswordShowError = showError
                    )
                }
            }

            ProfilePasswordEvents.ToggleExpandableArea ->
                toggleExpandableAreas(password = !_state.value.expandPasswordArea)

            ProfilePasswordEvents.ToggleOldPasswordView -> _state.update {
                it.copy(oldPasswordView = !_state.value.oldPasswordView)
            }

            ProfilePasswordEvents.ToggleNewPasswordView -> _state.update {
                it.copy(newPasswordView = !_state.value.newPasswordView)
            }

            ProfilePasswordEvents.ToggleNewPasswordMatchView -> _state.update {
                it.copy(newMatchPasswordView = !_state.value.newMatchPasswordView)
            }
        }
    }

    /**
     * Function to toggle which areas are expanded. By default all values for the areas are false.
     *
     * @param userInformation Boolean value stating whether the user information area is expanded or
     * not
     * @param emergencyContact Boolean value stating whether the emergency contact area is expanded
     * or not
     * @param password Boolean value stating whether the password area is expanded or not
     * @param companyInformation Boolean value stating whether the company information area is
     * expanded or not
     */
    private fun toggleExpandableAreas(
        userInformation: Boolean = false,
        emergencyContact: Boolean = false,
        password: Boolean = false,
        companyInformation: Boolean = false
    ) {
        _state.update {
            it.copy(
                expandUserInformationArea = userInformation,
                expandEmergencyContactArea = emergencyContact,
                expandPasswordArea = password,
                expandCompanyInformationArea = companyInformation
            )
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
        email: String = _user.value.email,
        password: String = _user.value.password
    ) {
        _user.update {
            it.copy(
                uid = User.generateUid(email = email),
                email = email,
                password = password
            )
        }
        _state.update { it.copy(changeMade = true) }
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
        _employee.update {
            it.copy(
                name = name,
                preferredName = preferredName,
                phone = phone,
                preferredContactMethod = prefContactMethod,
                dateOfBirth = dateOfBirth,
            )
        }
        _state.update { it.copy(changeMade = true) }
    }


    companion object {
        fun generateViewModel(
            useCases: ProfileUseCases,
            preferencesDataStore: UserPreferencesRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProfileViewModel(
                        useCases = useCases,
                        preferencesDataStore = preferencesDataStore
                    ) as T
                }
            }
        }
    }
}