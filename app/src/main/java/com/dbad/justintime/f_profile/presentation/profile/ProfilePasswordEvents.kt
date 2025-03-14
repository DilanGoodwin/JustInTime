package com.dbad.justintime.f_profile.presentation.profile

interface ProfilePasswordEvents {

    // Password Input Events
    data class OldPasswordInput(val oldPassword: String) : ProfilePasswordEvents
    data class NewPasswordInput(val newPassword: String) : ProfilePasswordEvents
    data class NewPasswordMatchInput(val newPassword: String) : ProfilePasswordEvents

    // Password Toggle Events
    data object ToggleExpandableArea : ProfilePasswordEvents
    data object ToggleOldPasswordView : ProfilePasswordEvents
    data object ToggleNewPasswordView : ProfilePasswordEvents
    data object ToggleNewPasswordMatchView : ProfilePasswordEvents
}