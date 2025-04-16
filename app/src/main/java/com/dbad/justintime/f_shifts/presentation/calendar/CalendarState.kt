package com.dbad.justintime.f_shifts.presentation.calendar

data class CalendarState(
    val loadingData: Boolean = true,
    val currentMonth: Int = 1,
    val currentYear: Int = 2000,
    val daysWithinMonth: ArrayList<Int> = ArrayList<Int>(),
    val selectedDate: Int = 0, // Default set to current day
)
