package com.dbad.justintime.f_profile.presentation.profile

import com.dbad.justintime.f_local_users_db.domain.model.util.PreferredContactMethod

sealed interface ProfileUserEvents {

    // User Input Events
    data class SetName(val name: String) : ProfileUserEvents
    data class SetPreferredName(val name: String) : ProfileUserEvents
    data class SetEmail(val email: String) : ProfileUserEvents
    data class SetDatOfBirth(val dateOfBirth: String) : ProfileUserEvents
    data class SetPhoneNumb(val phone: String) : ProfileUserEvents
    data class SetPrefContactMethod(val contactMethod: PreferredContactMethod) : ProfileUserEvents

    // User Toggle Events
    data object ToggleExpandedArea : ProfileUserEvents
    data object ToggleShowDatePicker : ProfileUserEvents
    data object TogglePrefContactDropDown : ProfileUserEvents
}