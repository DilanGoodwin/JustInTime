package com.dbad.justintime.f_shifts.presentation.shifts_list

import com.dbad.justintime.f_shifts.domain.model.ShiftEventTypes

data class ShiftsMainScreenState(
    val navProfilePage: () -> Unit = {},

    // Side Draw CheckBoxes
    val shiftsCheck: Boolean = true,
    val holidayCheck: Boolean = true,
    val unavailabilityCheck: Boolean = true,

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

    val newEventNotes: String = "",
)