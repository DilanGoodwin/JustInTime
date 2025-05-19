package com.dbad.justintime.f_shifts.presentation.shifts_list

import com.dbad.justintime.f_local_db.domain.model.Person
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes
import com.dbad.justintime.f_shifts.presentation.calendar.CalendarState
import com.dbad.justintime.f_shifts.presentation.individual_shifts.ShiftState

data class ShiftListState(
    val navProfilePage: () -> Unit = {},
    val isAdmin: Boolean = false,
    val employee: Person = Person(),
    val showSnackBar: Boolean = false,
    val snackBarMsg: String = "",

    // Side Draw CheckBoxes
    val onlyAdminShifts: Boolean = false,

    // New Shift Event
    val showNewShiftEventDialog: Boolean = false,
    val requestType: ShiftEventTypes = ShiftEventTypes.UNAVAILABILITY,
    val expandRequestTypeDropDown: Boolean = false,

    val datePickerHeadlineVal: String = "",
    val datePickerError: Boolean = false,
    val showDateTimeRangePickers: Boolean = false,
    val dateTimePickerState: Int = 0,

    // Date & Time Values
    val startDate: String = "",
    val endDate: String = "",
    val timeRequestStartHour: Int = 0,
    val timeRequestStartMinute: Int = 0,
    val timeRequestEndHour: Int = 0,
    val timeRequestEndMinute: Int = 0,

    // Admin Values
    val selectedPeople: List<String> = emptyList(),
    val selectedPeopleString: String = "",
    val selectedPeopleExpanded: Boolean = false,
    val role: String = "",
    val roleError: Boolean = false,
    val location: String = "",
    val locationError: Boolean = false,

    val eventApproved: Boolean = false,
    val newEventNotes: String = "",
    val calendarState: CalendarState = CalendarState(),
    val shiftState: ShiftState = ShiftState()
)