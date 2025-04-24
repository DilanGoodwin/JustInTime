package com.dbad.justintime.f_shifts.presentation.individual_shifts

import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.Person

data class ShiftState(
    val shifts: List<Event> = emptyList(),
    val holiday: List<Event> = emptyList(),
    val unavailability: List<Event> = emptyList(),
    val people: List<Person> = emptyList(),

    val selectedShiftOrEvent: Event = Event(),

    val showSelectedShift: Boolean = false,
    val isAdmin: Boolean = false,
    val readOnly: Boolean = true,

    val showTimePicker: Boolean = false,
    val timeError: Boolean = false,
    val timePickerState: Int = 0,
    val showDatePicker: Boolean = false,
    val dateError: Boolean = false,

    val shiftsCheck: Boolean = true,
    val holidayCheck: Boolean = true,
    val unavailabilityCheck: Boolean = true,
)