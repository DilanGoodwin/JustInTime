package com.dbad.justintime.f_login_register.presentation.user_details

import com.dbad.justintime.f_login_register.domain.model.util.PreferredContactMethod

data class UserDetailsState(
    // Nav Events
    val cancelEvent: () -> Unit = {},
    val registerEvent: () -> Unit = {},

    // User Input Fields
    val name: String = "",
    val preferredName: String = "",
    val phoneNumber: String = "",
    val prefContactMethod: PreferredContactMethod = PreferredContactMethod.NONE,
    val userDateOfBirth: String = "",

    // Emergency Contact Input Fields
    val emergencyContactName: String = "",
    val emergencyContactPrefName: String = "",
    val emergencyContactPhone: String = "",
    val emergencyContactEmail: String = "",
    val emergencyContactPrefContactMethod: PreferredContactMethod = PreferredContactMethod.NONE,

    // Error Values
    val showNameFieldError: Boolean = false,
    val showPhoneNumbFieldError: Boolean = false,
    val showDatePickerError: Boolean = false,

    // Emergency Contact Error Values
    val showEmergencyContactNameFieldError: Boolean = false,
    val showEmergencyContactPhoneError: Boolean = false,
    val showEmergencyContactEmailError: Boolean = false,

    // Expandable Fields
    val showDatePicker: Boolean = false,
    val prefContDropDownExpanded: Boolean = false,
    val emergencyContactAreaExpanded: Boolean = false,
    val emergencyContactPrefContDropDownExpand: Boolean = false,

    val email: String = "",
    val password: String = ""
)