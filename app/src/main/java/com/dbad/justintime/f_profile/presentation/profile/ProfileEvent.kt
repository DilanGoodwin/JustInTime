package com.dbad.justintime.f_profile.presentation.profile

sealed interface ProfileEvent {
    data class SetSignOutEvent(val signOut: () -> Unit) : ProfileEvent
    data class SetShiftNavEvent(val shiftNav: () -> Unit) : ProfileEvent

    data object SaveButton : ProfileEvent
    data object NavigateToShiftView : ProfileEvent
    data object SignOut : ProfileEvent
}