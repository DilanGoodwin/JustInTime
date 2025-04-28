package com.dbad.justintime.f_profile.presentation.profile

sealed interface ProfileEvent {
    data class SetSignOutEvent(val signOut: () -> Unit) : ProfileEvent
    data class SetShiftNavEvent(val shiftNav: () -> Unit) : ProfileEvent
    data class SetNewEmployeeEmail(val email: String) : ProfileEvent

    data object AddNewEmployee : ProfileEvent
    data object SaveEmployeeEmail : ProfileEvent
    data object CancelEmployeeEmail : ProfileEvent
    data object SignOut : ProfileEvent
    data object SaveButton : ProfileEvent
    data object NavigateToShiftView : ProfileEvent
}