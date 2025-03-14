package com.dbad.justintime.f_profile.presentation.profile

import com.dbad.justintime.core.domain.model.EmergencyContact
import com.dbad.justintime.core.domain.model.Employee
import com.dbad.justintime.core.domain.model.User
import com.dbad.justintime.f_login_register.domain.util.PasswordErrors

data class ProfileState(
    val changeMade: Boolean = false,

    // User Name States
    val expandUserInformationArea: Boolean = true,
    val user: User = User(),
    val employee: Employee = Employee(),
    val userNameError: Boolean = false,
    val userEmailError: Boolean = false,
    val userPhoneError: Boolean = false,
    val dateOfBirthError: Boolean = false,

    val showDateOfBirthPicker: Boolean = false,
    val expandPrefContactMethod: Boolean = false,

    // Expand Areas
    val expandEmergencyContactArea: Boolean = false,
    val expandPasswordArea: Boolean = false,
    val expandCompanyInformationArea: Boolean = false,

    // Password Area
    // Password Area - Old Password State
    val oldPassword: String = "",
    val oldPasswordView: Boolean = false,
    val oldPasswordShowError: Boolean = false,
    val oldPasswordErrorString: PasswordErrors = PasswordErrors.PASSWORD_NONE,

    // Password Area - New Password
    val newPassword: String = "",
    val newPasswordView: Boolean = false,
    val newPasswordShowError: Boolean = false,
    val newPasswordErrorString: PasswordErrors = PasswordErrors.PASSWORD_NONE,

    // Password Area - New Password Again
    val newMatchPassword: String = "",
    val newMatchPasswordView: Boolean = false,
    val newMatchPasswordShowError: Boolean = false,

    // Emergency Contact Area
    val emergencyContact: EmergencyContact = EmergencyContact(),
    val emergencyContactNameError: Boolean = false,
    val emergencyContactPhoneError: Boolean = false,
    val emergencyContactEmailError: Boolean = false,

    val emergencyContactExpandPrefContactMethod: Boolean = false,
    val emergencyContactExpandedRelation: Boolean = false,

    // Company Information Area
    val companyInformationManagerName: String = "",
    val companyInformationExpandedContractType: Boolean = false
)
