package com.dbad.justintime.f_shifts.presentation.shifts_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dbad.justintime.R
import com.dbad.justintime.f_shifts.domain.model.ShiftEventTypes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CalendarMainScreenViewModel() : ViewModel() {
    private val _state = MutableStateFlow(CalendarMainScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = CalendarMainScreenState()
    )

    fun onEvent(event: CalendarMainScreenEvent) {
        when (event) {
            is CalendarMainScreenEvent.SetProfileNavMainScreenEvent -> _state.update {
                it.copy(
                    navProfilePage = event.profileNav
                )
            }

            is CalendarMainScreenEvent.ToggleFilters -> {
                when (event.filter) {
                    R.string.shifts -> _state.update { it.copy(shiftsCheck = !state.value.shiftsCheck) }
                    R.string.holiday -> _state.update { it.copy(holidayCheck = !state.value.holidayCheck) }
                    R.string.unavailability -> _state.update { it.copy(unavailabilityCheck = !state.value.unavailabilityCheck) }
                }
            }

            CalendarMainScreenEvent.ToggleNewMainScreenEventDialog -> _state.update {
                it.copy(showNewShiftEventDialog = !state.value.showNewShiftEventDialog)
            }

            CalendarMainScreenEvent.CancelNewMainScreenEventDialog -> {
                _state.update {
                    it.copy(
                        showNewShiftEventDialog = false,
                        requestType = ShiftEventTypes.UNAVAILABILITY,
                        dateTimePickerState = 0
                    )
                }
            }

            CalendarMainScreenEvent.ConfirmNewMainScreenEventDialog -> {} //TODO should add a request into the database

            CalendarMainScreenEvent.ToggleRequestTypeDropDown -> _state.update {
                it.copy(expandRequestTypeDropDown = !state.value.expandRequestTypeDropDown)
            }

            is CalendarMainScreenEvent.SetRequestType -> _state.update {
                it.copy(
                    requestType = event.requestType,
                    expandRequestTypeDropDown = false
                )
            }

            CalendarMainScreenEvent.ToggleDateSelectorDropDown -> _state.update {
                it.copy(showDateTimeRangePickers = !state.value.showDateTimeRangePickers)
            }

            CalendarMainScreenEvent.CancelDateSelectorDialog -> {
                // Close the window at its current point, if date string is blank then reset all details
                if (state.value.startDate.isEmpty()) {
                    _state.update {
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
                _state.update { it.copy(dateTimePickerState = 0, showDateTimeRangePickers = false) }
            }

            is CalendarMainScreenEvent.ConfirmRangeDateSelector -> {
                _state.update {
                    it.copy(
                        dateTimePickerState = 1,
                        startDate = event.startDate,
                        endDate = event.endDate
                    )
                }
                generateDatePickerStringVal()
            }

            is CalendarMainScreenEvent.ConfirmStartTimeSelector -> {
                _state.update {
                    it.copy(
                        dateTimePickerState = 2,
                        timeRequestStartHour = event.hour,
                        timeRequestStartMinute = event.minute
                    )
                }
                generateDatePickerStringVal()
            }

            is CalendarMainScreenEvent.ConfirmEndTimeSelector -> {
                _state.update {
                    it.copy(
                        showDateTimeRangePickers = false,
                        dateTimePickerState = 0,
                        timeRequestEndHour = event.hour,
                        timeRequestEndMinute = event.minute
                    )
                }
                generateDatePickerStringVal()
            }

            is CalendarMainScreenEvent.UpdateNotes -> _state.update { it.copy(newEventNotes = event.notes) }
        }
    }

    /*
    Performing string formatting to make the date and time organised when displayed to the end user
    If the submitted hour & minute is 0 then we assume the time off is for the entire day
     */
    private fun generateDatePickerStringVal() {
        var returnString: String = state.value.startDate
        if (state.value.timeRequestStartHour != 0 || state.value.timeRequestStartMinute != 0) {
            returnString += " ${state.value.timeRequestStartHour}:${state.value.timeRequestStartMinute}"
        }

        if (state.value.endDate.isNotEmpty()) {
            returnString += " - ${state.value.endDate}"
        }

        if (state.value.timeRequestEndHour != 0 || state.value.timeRequestEndMinute != 0) {
            returnString += " ${state.value.timeRequestEndHour}:${state.value.timeRequestEndMinute}"
        }

        _state.update { it.copy(datePickerHeadlineVal = returnString) }
    }

    companion object {
        fun generateViewModel(): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CalendarMainScreenViewModel() as T
                }
            }
        }
    }
}