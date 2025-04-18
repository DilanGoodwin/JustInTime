package com.dbad.justintime.f_shifts.presentation.shifts_list

import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes

sealed interface ShiftListEvents {
    data class SetProfileNavMainScreenEvents(val profileNav: () -> Unit) : ShiftListEvents

    data class ToggleFilters(val filter: Int) : ShiftListEvents

    // New Calendar Event Dialog
    data object ToggleNewMainScreenEventsDialog : ShiftListEvents
    data object CancelNewMainScreenEventsDialog : ShiftListEvents
    data object ConfirmNewMainScreenEventsDialog : ShiftListEvents

    // New Calendar Event Dialog - Request Type
    data object ToggleRequestTypeDropDown : ShiftListEvents
    data class SetRequestType(val requestType: ShiftEventTypes) : ShiftListEvents

    // Admin Actions
    data object ToggleSelectedPeopleDropDown : ShiftListEvents
    data class AddEmployee(val person: String) : ShiftListEvents
    data class SetRole(val role: String) : ShiftListEvents
    data class SetLocation(val location: String) : ShiftListEvents

    // New Calendar Event Dialog - Date Selector
    data object ToggleDateSelectorDropDown : ShiftListEvents
    data object CancelDateSelectorDialog : ShiftListEvents
    data class ConfirmRangeDateSelector(val startDate: String, val endDate: String) :
        ShiftListEvents

    data class ConfirmStartTimeSelector(val hour: Int, val minute: Int) : ShiftListEvents
    data class ConfirmEndTimeSelector(val hour: Int, val minute: Int) : ShiftListEvents

    // New Calendar Event Dialog - Notes
    data class UpdateNotes(val notes: String) : ShiftListEvents
}