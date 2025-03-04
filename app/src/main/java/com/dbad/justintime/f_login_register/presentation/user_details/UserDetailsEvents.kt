package com.dbad.justintime.f_login_register.presentation.user_details

import com.dbad.justintime.f_login_register.domain.model.util.PreferredContactMethod

sealed interface UserDetailsEvents {
    // Navigation Events
    data class SetCancelEvent(val cancelAction: () -> Unit) : UserDetailsEvents
    data class SetRegisterEvent(val registerAction: () -> Unit) : UserDetailsEvents

    // User Input Events
    data class SetName(val name: String) : UserDetailsEvents
    data class SetPrefName(val name: String) : UserDetailsEvents
    data class SetPhoneNumb(val phone: String) : UserDetailsEvents
    data class SetPrefContactMethod(val contactMethod: PreferredContactMethod) : UserDetailsEvents

    // Emergency Contact Events
    data class SetEmergencyContactName(val name: String) : UserDetailsEvents
    data class SetEmergencyContactPrefName(val name: String) : UserDetailsEvents
    data class SetEmergencyContactPhoneNumb(val phone: String) : UserDetailsEvents
    data class SetEmergencyContactEmail(val email: String) : UserDetailsEvents
    data class SetEmergencyContactPrefContactMethod(val contactMethod: PreferredContactMethod) :
        UserDetailsEvents

    // Expanded Toggles Events
    data object TogglePrefContactDropDown : UserDetailsEvents
    data object ToggleEmergencyContactArea : UserDetailsEvents

    data object ToggleEmergencyContactPrefContactDropDown : UserDetailsEvents

    // Buttons Click Event
    data object CancelEvent : UserDetailsEvents
    data object RegisterEvent : UserDetailsEvents

    // Saving Passed Data
    data class SetEmail(val email: String) : UserDetailsEvents
    data class SetPassword(val password: String) : UserDetailsEvents
}