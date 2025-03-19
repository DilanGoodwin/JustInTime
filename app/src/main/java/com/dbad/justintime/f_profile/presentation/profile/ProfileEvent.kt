package com.dbad.justintime.f_profile.presentation.profile

sealed interface ProfileEvent {
    data object SaveButton : ProfileEvent
}