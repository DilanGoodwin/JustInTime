package com.dbad.justintime.f_shifts.presentation.calendar

sealed interface CalendarEvents {
    data object MonthGoBack : CalendarEvents
    data object MonthGoForward : CalendarEvents
    data object ReturnCurrentMonth : CalendarEvents

    data class SelectADay(val day: Int) : CalendarEvents
}