package com.dbad.justintime.f_shifts.presentation.individual_shifts

import com.dbad.justintime.f_local_db.domain.model.Event

sealed interface ShiftEvents {
    data class SelectEvent(val event: Event) : ShiftEvents

    data object ToggleSelectedEvent : ShiftEvents

    data object ToggleTimePicker : ShiftEvents
    data object ToggleDatePicker : ShiftEvents
    data object ToggleEditMode : ShiftEvents

    data object RemoveEvent : ShiftEvents
    data object ApproveEvent : ShiftEvents

    data class SetDate(val startDate: String, val endDate: String) : ShiftEvents
    data class SetTime(val hour: Int, val minute: Int) : ShiftEvents
    data class SetRole(val role: String) : ShiftEvents
    data class SetLocation(val location: String) : ShiftEvents
    data class SetNotes(val notes: String) : ShiftEvents

}