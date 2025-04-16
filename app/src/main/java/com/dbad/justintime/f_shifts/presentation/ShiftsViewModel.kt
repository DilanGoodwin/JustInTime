package com.dbad.justintime.f_shifts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dbad.justintime.R
import com.dbad.justintime.f_shifts.domain.model.ShiftEventTypes
import com.dbad.justintime.f_shifts.presentation.calendar.CalendarEvents
import com.dbad.justintime.f_shifts.presentation.calendar.CalendarState
import com.dbad.justintime.f_shifts.presentation.shifts_list.ShiftsMainScreenEvents
import com.dbad.justintime.f_shifts.presentation.shifts_list.ShiftsMainScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.YearMonth
import java.util.Calendar

class ShiftsViewModel() : ViewModel() {

    private val _state = MutableStateFlow(ShiftsMasterState())
    private val _mainScreenState = MutableStateFlow(ShiftsMainScreenState())
    private val _calendarState = MutableStateFlow(CalendarState())

    val state = combine(_state, _mainScreenState, _calendarState)
    { state, mainScreenState, calendarState ->
        state.copy(
            mainScreenState = mainScreenState,
            calendarState = calendarState
        )
    }.onStart { onStartDetails() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = ShiftsMasterState()
    )

    private fun onStartDetails() {
        val calInst = Calendar.getInstance()
        val month = calInst.get(Calendar.MONTH) + 1
        val year = calInst.get(Calendar.YEAR)
        generateCalendarInformation(month = month, year = year)
    }

    fun mainScreenEvents(event: ShiftsMainScreenEvents) {
        when (event) {
            is ShiftsMainScreenEvents.SetProfileNavMainScreenEvents -> _mainScreenState.update {
                it.copy(
                    navProfilePage = event.profileNav
                )
            }

            is ShiftsMainScreenEvents.ToggleFilters -> {
                when (event.filter) {
                    R.string.shifts -> _mainScreenState.update { it.copy(shiftsCheck = !state.value.mainScreenState.shiftsCheck) }
                    R.string.holiday -> _mainScreenState.update { it.copy(holidayCheck = !state.value.mainScreenState.holidayCheck) }
                    R.string.unavailability -> _mainScreenState.update { it.copy(unavailabilityCheck = !state.value.mainScreenState.unavailabilityCheck) }
                }
            }

            ShiftsMainScreenEvents.ToggleNewMainScreenEventsDialog -> _mainScreenState.update {
                it.copy(showNewShiftEventDialog = !state.value.mainScreenState.showNewShiftEventDialog)
            }

            ShiftsMainScreenEvents.CancelNewMainScreenEventsDialog -> {
                _mainScreenState.update {
                    it.copy(
                        showNewShiftEventDialog = false,
                        requestType = ShiftEventTypes.UNAVAILABILITY,
                        dateTimePickerState = 0
                    )
                }
            }

            ShiftsMainScreenEvents.ConfirmNewMainScreenEventsDialog -> {} //TODO should add a request into the database

            ShiftsMainScreenEvents.ToggleRequestTypeDropDown -> _mainScreenState.update {
                it.copy(expandRequestTypeDropDown = !state.value.mainScreenState.expandRequestTypeDropDown)
            }

            is ShiftsMainScreenEvents.SetRequestType -> _mainScreenState.update {
                it.copy(
                    requestType = event.requestType,
                    expandRequestTypeDropDown = false
                )
            }

            ShiftsMainScreenEvents.ToggleDateSelectorDropDown -> _mainScreenState.update {
                it.copy(showDateTimeRangePickers = !state.value.mainScreenState.showDateTimeRangePickers)
            }

            ShiftsMainScreenEvents.CancelDateSelectorDialog -> {
                // Close the window at its current point, if date string is blank then reset all details
                if (state.value.mainScreenState.startDate.isEmpty()) {
                    _mainScreenState.update {
                        it.copy(
                            startDate = "",
                            endDate = "",
                            timeRequestStartHour = 0,
                            timeRequestStartMinute = 0,
                            timeRequestEndHour = 0,
                            timeRequestEndMinute = 0
                        )
                    }
                }
                _mainScreenState.update {
                    it.copy(
                        dateTimePickerState = 0,
                        showDateTimeRangePickers = false
                    )
                }
            }

            is ShiftsMainScreenEvents.ConfirmRangeDateSelector -> {
                _mainScreenState.update {
                    it.copy(
                        dateTimePickerState = 1,
                        startDate = event.startDate,
                        endDate = event.endDate
                    )
                }
                generateDatePickerStringVal()
            }

            is ShiftsMainScreenEvents.ConfirmStartTimeSelector -> {
                _mainScreenState.update {
                    it.copy(
                        dateTimePickerState = 2,
                        timeRequestStartHour = event.hour,
                        timeRequestStartMinute = event.minute
                    )
                }
                generateDatePickerStringVal()
            }

            is ShiftsMainScreenEvents.ConfirmEndTimeSelector -> {
                _mainScreenState.update {
                    it.copy(
                        showDateTimeRangePickers = false,
                        dateTimePickerState = 0,
                        timeRequestEndHour = event.hour,
                        timeRequestEndMinute = event.minute
                    )
                }
                generateDatePickerStringVal()
            }

            is ShiftsMainScreenEvents.UpdateNotes -> _mainScreenState.update { it.copy(newEventNotes = event.notes) }
        }
    }

    fun calendarEvents(event: CalendarEvents) {
        when (event) {
            CalendarEvents.MonthGoBack -> calculateNewMonthYearValue(_calendarState.value.currentMonth - 1)
            CalendarEvents.MonthGoForward -> calculateNewMonthYearValue(_calendarState.value.currentMonth + 1)
            CalendarEvents.ReturnCurrentMonth -> {
                val calInst = Calendar.getInstance()
                val month = calInst.get(Calendar.MONTH) + 1
                val year = calInst.get(Calendar.YEAR)
                generateCalendarInformation(month = month, year = year)
            }

            is CalendarEvents.SelectADay -> _calendarState.update { it.copy(selectedDate = event.day) }
        }
    }

    /*
    Performing string formatting to make the date and time organised when displayed to the end user
    If the submitted hour & minute is 0 then we assume the time off is for the entire day
     */
    private fun generateDatePickerStringVal() {
        var returnString: String = state.value.mainScreenState.startDate
        if (state.value.mainScreenState.timeRequestStartHour != 0 || state.value.mainScreenState.timeRequestStartMinute != 0) {
            returnString += " ${state.value.mainScreenState.timeRequestStartHour}:${state.value.mainScreenState.timeRequestStartMinute}"
        }

        if (state.value.mainScreenState.endDate.isNotEmpty()) {
            returnString += " - ${state.value.mainScreenState.endDate}"
        }

        if (state.value.mainScreenState.timeRequestEndHour != 0 || state.value.mainScreenState.timeRequestEndMinute != 0) {
            returnString += " ${state.value.mainScreenState.timeRequestEndHour}:${state.value.mainScreenState.timeRequestEndMinute}"
        }

        _mainScreenState.update { it.copy(datePickerHeadlineVal = returnString) }
    }

    private fun generateCalendarInformation(month: Int, year: Int) {
        val instCalendar = Calendar.getInstance()
        val currentDay = instCalendar.get(Calendar.DAY_OF_MONTH)
        val daysWithinMonth = YearMonth.of(year, month).lengthOfMonth()
        val startDayOfMonth = YearMonth.of(year, month).atDay(1).dayOfWeek.value - 1

        val monthDates = ArrayList<Int>()

        // Padding days when the month does not start on Monday
        for (i in 1..startDayOfMonth) {
            monthDates.add(0)
        }

        for (i in 1..daysWithinMonth) {
            monthDates.add(i)
        }

        _calendarState.update {
            it.copy(
                loadingData = false,
                currentMonth = month,
                currentYear = year,
                daysWithinMonth = monthDates,
                selectedDate = currentDay
            )
        }
    }

    /*
    Need to work out whether we need to go forwards or backwards by a year depending on how many months
    forwards/backwards the user has gone
     */
    private fun calculateNewMonthYearValue(month: Int) {
        var newMonth: Int = month
        var newYear: Int = _calendarState.value.currentYear

        if (month < 1) {
            newMonth = 12
            newYear--
        }

        if (month > 12) {
            newMonth = 1
            newYear++
        }

        generateCalendarInformation(month = newMonth, year = newYear)
    }

    companion object {
        fun generateViewModel(): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ShiftsViewModel() as T
                }
            }
        }
    }
}