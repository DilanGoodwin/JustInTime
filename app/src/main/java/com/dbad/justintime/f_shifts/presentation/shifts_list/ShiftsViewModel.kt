package com.dbad.justintime.f_shifts.presentation.shifts_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.DATE_FORMATTER
import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.Person
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes
import com.dbad.justintime.f_shifts.domain.use_case.ShiftUseCases
import com.dbad.justintime.f_shifts.presentation.calendar.CalendarEvents
import com.dbad.justintime.f_shifts.presentation.calendar.CalendarState
import com.dbad.justintime.f_shifts.presentation.individual_shifts.ShiftState
import com.dbad.justintime.f_user_auth.domain.repository.AuthRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.util.Calendar
import java.util.Date

class ShiftsViewModel(
    private val useCases: ShiftUseCases,
    private val authUser: AuthRepo
) : ViewModel() {

    private val _state = MutableStateFlow(ShiftListState())
    private val _selectedPeople = MutableStateFlow(listOf<String>())
    private val _calendarState = MutableStateFlow(CalendarState())

    // Generate individual flow lists from database
    private val _shiftsList = useCases.getEvents(
        type = ShiftEventTypes.SHIFTS,
        userOnlyEvents = _state.value.onlyAdminShifts,
        userId = _state.value.employee.employeeUid
    ).stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), emptyList())
    private val _holidayList = useCases.getEvents(
        type = ShiftEventTypes.HOLIDAY,
        userOnlyEvents = _state.value.onlyAdminShifts,
        userId = _state.value.employee.employeeUid
    ).stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), emptyList())
    private val _unavailabilityList = useCases.getEvents(
        type = ShiftEventTypes.UNAVAILABILITY,
        userOnlyEvents = _state.value.onlyAdminShifts,
        userId = _state.value.employee.employeeUid
    ).stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), emptyList())

    private var _emptyShiftState = MutableStateFlow(ShiftState())
    private val _shiftState =
        combine(_emptyShiftState, _shiftsList, _holidayList, _unavailabilityList)
        { emptyState, shifts, holiday, unavailable ->
            emptyState.copy(
                shifts = shifts,
                holiday = holiday,
                unavailability = unavailable
            )
        }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), ShiftState())

    // Generate master state of ViewModel
    val state = combine(_state, _selectedPeople, _calendarState, _shiftState)
    { state, selectedPeople, calendarState, shiftState ->
        state.copy(
            selectedPeople = selectedPeople,
            calendarState = calendarState,
            shiftState = shiftState
        )
    }.onStart { onStartDetails() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = ShiftListState()
    )

    private fun onStartDetails() {
        val calInst = Calendar.getInstance()
        val month = calInst.get(Calendar.MONTH) + 1
        val year = calInst.get(Calendar.YEAR)
        generateCalendarInformation(month = month, year = year)

        getUserInformation()

        if (state.value.isAdmin) {
            //TODO Grab list of people
            //TODO Grab requests
        }

        _calendarState.update { it.copy(loadingData = false) }
    }

    // Get Employee information
    private fun getUserInformation() {
        var loadAttempts = 0
        val userUid = User.Companion.generateUid(email = authUser.getEmail())

        viewModelScope.launch {
            while (loadAttempts < 3) {
                val user = useCases.getUser(user = User(uid = userUid))

                try {
                    if (user.uid.isNotEmpty() && user.employee.isNotEmpty()) {
                        val employee =
                            useCases.getEmployee(employee = Employee(uid = user.employee))
                        val personCreation =
                            Person(employeeUid = employee.uid, name = employee.name)

                        _state.update {
                            it.copy(
                                employee = personCreation,
                                isAdmin = employee.isAdmin
                            )
                        }
                        _emptyShiftState.update { it.copy(people = listOf(personCreation)) }
                    }
                } catch (e: NullPointerException) {
                    Log.d("ShiftsViewModel", "Error reading user \n Error: ${e.message}")
                }

                loadAttempts++
                delay(timeMillis = 1000L)
            }
        }

        if (loadAttempts >= 3) {
            /*
            If we failed to grab user information move to Profile and try again, if Profile fails
            then the user is automatically logged out
             */
            _state.value.navProfilePage()
        }
    }

    fun onShiftListEvents(event: ShiftListEvents) {
        when (event) {
            is ShiftListEvents.SetProfileNavMainScreenEvents -> _state.update {
                it.copy(
                    navProfilePage = event.profileNav
                )
            }

            is ShiftListEvents.ToggleFilters -> {
                when (event.filter) {
                    R.string.shifts -> _emptyShiftState.update { it.copy(shiftsCheck = !state.value.shiftState.shiftsCheck) }
                    R.string.holiday -> _emptyShiftState.update { it.copy(holidayCheck = !state.value.shiftState.holidayCheck) }
                    R.string.unavailability -> _emptyShiftState.update { it.copy(unavailabilityCheck = !state.value.shiftState.unavailabilityCheck) }
                    R.string.onlyAdminShifts -> _state.update { it.copy(onlyAdminShifts = !state.value.onlyAdminShifts) }
                }
            }

            ShiftListEvents.ToggleNewMainScreenEventsDialog -> _state.update {
                it.copy(showNewShiftEventDialog = !state.value.showNewShiftEventDialog)
            }

            ShiftListEvents.CancelNewMainScreenEventsDialog -> {
                _state.update {
                    it.copy(
                        showNewShiftEventDialog = false,
                        requestType = ShiftEventTypes.UNAVAILABILITY,
                        dateTimePickerState = 0
                    )
                }
            }

            ShiftListEvents.ConfirmNewMainScreenEventsDialog -> {

                // Are the dates valid
                if (!useCases.validateDate(
                        currentDate = DATE_FORMATTER.format(Date()),
                        startDate = state.value.startDate,
                        endDate = state.value.endDate
                    )
                ) {
                    _state.update { it.copy(datePickerError = true) }
                    return
                }

                // Are the times valid
                if (state.value.startDate == state.value.endDate &&
                    !(state.value.timeRequestStartHour == state.value.timeRequestEndHour &&
                            state.value.timeRequestStartMinute == state.value.timeRequestEndMinute)
                ) {
                    if ((state.value.timeRequestStartHour > state.value.timeRequestEndHour) ||
                        (state.value.timeRequestStartHour == state.value.timeRequestEndHour &&
                                state.value.timeRequestStartMinute > state.value.timeRequestEndMinute)
                    ) {
                        _state.update { it.copy(datePickerError = true) }
                        return
                    }
                }

                if (_state.value.requestType == ShiftEventTypes.SHIFTS) {
                    // Has a role been entered
                    if (state.value.role.isEmpty()) {
                        _state.update { it.copy(roleError = true) }
                        return
                    }

                    // Has a location been entered
                    if (state.value.location.isEmpty()) {
                        _state.update { it.copy(locationError = true) }
                        return
                    }

                    _state.update { it.copy(eventApproved = true) }
                } else {
                    addPersonToList(person = state.value.employee.employeeUid)
                }

                val newShiftOrEvent = Event(
                    uid = Event.Companion.generateUid(employeeId = state.value.employee.employeeUid),
                    type = state.value.requestType,
                    startDate = state.value.startDate,
                    startTime = "${state.value.timeRequestStartHour}:${state.value.timeRequestStartMinute}",
                    endDate = state.value.endDate,
                    endTime = "${state.value.timeRequestEndHour}:${state.value.timeRequestEndMinute}",
                    rolePosition = state.value.role,
                    employees = Event.convertEmployeesString(employees = state.value.selectedPeople),
                    location = state.value.location,
                    approved = state.value.eventApproved,
                    notes = state.value.newEventNotes
                )

                viewModelScope.launch { useCases.upsertEvents(event = newShiftOrEvent) }

                // Reset state once saved the event
                _selectedPeople.value = emptyList<String>()
                _state.update {
                    it.copy(
                        showNewShiftEventDialog = false,
                        requestType = ShiftEventTypes.UNAVAILABILITY,
                        datePickerHeadlineVal = "",
                        datePickerError = false,
                        startDate = "",
                        endDate = "",
                        timeRequestStartHour = 0,
                        timeRequestStartMinute = 0,
                        timeRequestEndHour = 0,
                        timeRequestEndMinute = 0,
                        role = "",
                        roleError = false,
                        location = "",
                        locationError = false,
                        eventApproved = false,
                        newEventNotes = ""
                    )
                }

            }

            is ShiftListEvents.AddEmployee -> addPersonToList(person = event.person)
            is ShiftListEvents.SetRole -> _state.update { it.copy(role = event.role) }
            is ShiftListEvents.SetLocation -> _state.update { it.copy(location = event.location) }

            ShiftListEvents.ToggleRequestTypeDropDown -> _state.update {
                it.copy(expandRequestTypeDropDown = !state.value.expandRequestTypeDropDown)
            }

            is ShiftListEvents.SetRequestType -> _state.update {
                it.copy(
                    requestType = event.requestType,
                    expandRequestTypeDropDown = false
                )
            }

            ShiftListEvents.ToggleDateSelectorDropDown -> _state.update {
                it.copy(showDateTimeRangePickers = !state.value.showDateTimeRangePickers)
            }

            ShiftListEvents.CancelDateSelectorDialog -> {
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
                _state.update {
                    it.copy(
                        dateTimePickerState = 0,
                        showDateTimeRangePickers = false
                    )
                }
            }

            is ShiftListEvents.ConfirmRangeDateSelector -> {
                _state.update {
                    it.copy(
                        dateTimePickerState = 1,
                        startDate = event.startDate,
                        endDate = if (event.endDate.isEmpty()) event.startDate else event.endDate
                    )
                }
                generateDatePickerStringVal()
            }

            is ShiftListEvents.ConfirmStartTimeSelector -> {
                _state.update {
                    it.copy(
                        dateTimePickerState = 2,
                        timeRequestStartHour = event.hour,
                        timeRequestStartMinute = event.minute
                    )
                }
                generateDatePickerStringVal()
            }

            is ShiftListEvents.ConfirmEndTimeSelector -> {
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

            is ShiftListEvents.UpdateNotes -> _state.update { it.copy(newEventNotes = event.notes) }
            ShiftListEvents.ToggleSelectedPeopleDropDown -> _state.update {
                it.copy(selectedPeopleExpanded = !state.value.selectedPeopleExpanded)
            }
        }
    }

    fun onCalendarEvents(event: CalendarEvents) {
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

    private fun addPersonToList(person: String) {
        val newPersonList = ArrayList(_selectedPeople.value)
        newPersonList.add(person)
        _selectedPeople.value = newPersonList

        var peopleString = ""
        for (person in state.value.shiftState.people) {
            if (person.employeeUid in _selectedPeople.value) {
                peopleString += " ${person.name}"
            }
        }
        _state.update { it.copy(selectedPeopleString = peopleString) }
    }

    companion object {
        fun generateViewModel(
            useCases: ShiftUseCases,
            authUser: AuthRepo
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ShiftsViewModel(
                        useCases = useCases,
                        authUser = authUser
                    ) as T
                }
            }
        }
    }
}