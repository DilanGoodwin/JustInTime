package com.dbad.justintime.f_shifts.presentation

import com.dbad.justintime.f_shifts.presentation.calendar.CalendarState
import com.dbad.justintime.f_shifts.presentation.shifts_list.ShiftsMainScreenState

data class ShiftsMasterState(
    val mainScreenState: ShiftsMainScreenState = ShiftsMainScreenState(),
    val calendarState: CalendarState = CalendarState()
)
