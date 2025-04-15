package com.dbad.justintime.f_shifts.presentation.shifts_list

import com.dbad.justintime.f_shifts.domain.model.ShiftEventTypes

sealed interface CalendarMainScreenEvent {
    data class SetProfileNavMainScreenEvent(val profileNav: () -> Unit) : CalendarMainScreenEvent

    data class ToggleFilters(val filter: Int) : CalendarMainScreenEvent

    // New Calendar Event Dialog
    data object ToggleNewMainScreenEventDialog : CalendarMainScreenEvent
    data object CancelNewMainScreenEventDialog : CalendarMainScreenEvent
    data object ConfirmNewMainScreenEventDialog : CalendarMainScreenEvent

    // New Calendar Event Dialog - Request Type
    data object ToggleRequestTypeDropDown : CalendarMainScreenEvent
    data class SetRequestType(val requestType: ShiftEventTypes) : CalendarMainScreenEvent

    // New Calendar Event Dialog - Date Selector
    data object ToggleDateSelectorDropDown : CalendarMainScreenEvent
    data object CancelDateSelectorDialog : CalendarMainScreenEvent
    data class ConfirmRangeDateSelector(val startDate: String, val endDate: String) :
        CalendarMainScreenEvent

    data class ConfirmStartTimeSelector(val hour: Int, val minute: Int) : CalendarMainScreenEvent
    data class ConfirmEndTimeSelector(val hour: Int, val minute: Int) : CalendarMainScreenEvent

    // New Calendar Event Dialog - Notes
    data class UpdateNotes(val notes: String) : CalendarMainScreenEvent
}