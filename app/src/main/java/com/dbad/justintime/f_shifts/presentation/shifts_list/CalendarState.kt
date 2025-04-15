package com.dbad.justintime.f_shifts.presentation.shifts_list

import com.dbad.justintime.R
import com.dbad.justintime.f_shifts.domain.model.ShiftEventTypes

data class CalendarState(
    val sideDrawOpen: Boolean = false,
    val navProfilePage: () -> Unit = {},

    // Side Draw CheckBoxes
    val shiftsCheck: Boolean = true,
    val holidayCheck: Boolean = true,
    val unavailabilityCheck: Boolean = true,

    // New Shift Event
    val requestType: ShiftEventTypes = ShiftEventTypes.UNAVAILABILITY,
    val expandRequestTypeDropDown: Boolean = false,

    val showDateTimeRangePickers: Boolean = false,
    val dateTimePickerState: Int = 0,

    val datePickerHeadlineVal: Int = R.string.selectDate
)
