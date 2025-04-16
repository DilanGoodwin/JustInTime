package com.dbad.justintime.f_shifts.presentation.shifts_list

import com.dbad.justintime.f_shifts.domain.model.ShiftEventTypes

sealed interface ShiftsMainScreenEvents {
    data class SetProfileNavMainScreenEvents(val profileNav: () -> Unit) : ShiftsMainScreenEvents

    data class ToggleFilters(val filter: Int) : ShiftsMainScreenEvents

    // New Calendar Event Dialog
    data object ToggleNewMainScreenEventsDialog : ShiftsMainScreenEvents
    data object CancelNewMainScreenEventsDialog : ShiftsMainScreenEvents
    data object ConfirmNewMainScreenEventsDialog : ShiftsMainScreenEvents

    // New Calendar Event Dialog - Request Type
    data object ToggleRequestTypeDropDown : ShiftsMainScreenEvents
    data class SetRequestType(val requestType: ShiftEventTypes) : ShiftsMainScreenEvents

    // New Calendar Event Dialog - Date Selector
    data object ToggleDateSelectorDropDown : ShiftsMainScreenEvents
    data object CancelDateSelectorDialog : ShiftsMainScreenEvents
    data class ConfirmRangeDateSelector(val startDate: String, val endDate: String) :
        ShiftsMainScreenEvents

    data class ConfirmStartTimeSelector(val hour: Int, val minute: Int) : ShiftsMainScreenEvents
    data class ConfirmEndTimeSelector(val hour: Int, val minute: Int) : ShiftsMainScreenEvents

    // New Calendar Event Dialog - Notes
    data class UpdateNotes(val notes: String) : ShiftsMainScreenEvents
}