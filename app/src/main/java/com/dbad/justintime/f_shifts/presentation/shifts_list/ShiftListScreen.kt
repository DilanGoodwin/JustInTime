package com.dbad.justintime.f_shifts.presentation.shifts_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Approval
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.BackgroundOutlineWithButtons
import com.dbad.justintime.core.presentation.util.DateSelectorField
import com.dbad.justintime.core.presentation.util.LabelledTextDropDownFields
import com.dbad.justintime.core.presentation.util.LabelledTextInputFields
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationRole
import com.dbad.justintime.core.presentation.util.TestTagShiftLocation
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.core.presentation.util.formatDateToString
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes
import com.dbad.justintime.f_shifts.presentation.calendar.CalendarEvents
import com.dbad.justintime.f_shifts.presentation.calendar.CalendarView
import com.dbad.justintime.f_shifts.presentation.individual_shifts.ShiftEvents
import com.dbad.justintime.f_shifts.presentation.individual_shifts.ShiftListView
import com.dbad.justintime.ui.theme.JustInTimeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Stateful
@Composable
fun ShiftListScreen(
    viewModel: ShiftsViewModel,
    onNavProfile: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val event = viewModel::onShiftListEvents
    event(ShiftListEvents.SetProfileNavMainScreenEvents(onNavProfile))

    ShiftListScreen(
        state = state,
        onEvent = event,
        calendarEvents = viewModel::onCalendarEvents,
        shiftEvents = viewModel::onShiftEvent
    )
}

// Stateless
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ShiftListScreen(
    state: ShiftListState,
    onEvent: (ShiftListEvents) -> Unit,
    calendarEvents: (CalendarEvents) -> Unit,
    shiftEvents: (ShiftEvents) -> Unit
) {
    /*
    This is so that the side draw can be opened and closed with an animation, when trying to do this
    within the ViewModelScope the application crashes as the viewModel is attempting to change the
    front-end without a recompilation being triggered.
     */
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Setting up SnackBar for messages
    val snackBarState = remember { SnackbarHostState() }

    ModalNavigationDrawer(
        drawerContent = { FilterDraw(state = state, onEvent = onEvent) },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                ShiftTopAppBar(
                    scope = scope,
                    drawerState = drawerState,
                    onEvent = onEvent
                )
            },
            bottomBar = { ShiftBottomNavBar(state = state) },
            snackbarHost = {
                SnackbarHost(hostState = snackBarState)

                if (state.snackBarMsg.isNotBlank()) {
                    scope.launch {
                        snackBarState.showSnackbar(
                            message = state.snackBarMsg,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            },
            floatingActionButton = {
                SmallFloatingActionButton(onClick = { onEvent(ShiftListEvents.ToggleNewMainScreenEventsDialog) }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.new_event)
                    )
                    NewShiftEventDialogWindow(state = state, onEvent = onEvent)
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                if (state.calendarState.loadingData) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(64.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                } else {
                    CalendarView(
                        calendarState = state.calendarState,
                        calendarEvents = calendarEvents
                    )

                    Spacer(modifier = Modifier.height(height = 10.dp))

                    ShiftListView(state = state.shiftState, onEvent = shiftEvents)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftTopAppBar(
    scope: CoroutineScope,
    drawerState: DrawerState,
    onEvent: (ShiftListEvents) -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.calendar)) },
        navigationIcon = {
            // Side Menu for filters
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.filter_options)
                )
            }
        },
        actions = {
            IconButton(onClick = { onEvent(ShiftListEvents.RefreshDisplayedEvents(msg = "Updating Events")) }) {
                Icon(
                    imageVector = Icons.Filled.Autorenew,
                    contentDescription = ""
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ShiftBottomNavBar(state: ShiftListState) {
    NavigationBar {
        // Calendar Page
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = stringResource(R.string.calendar)
                )
            },
            label = { Text(text = stringResource(R.string.calendar)) }
        )

        // Profile Page
        NavigationBarItem(
            selected = false,
            onClick = { state.navProfilePage() },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Approval,
                    contentDescription = stringResource(R.string.profile)
                )
            },
            label = { Text(text = stringResource(R.string.profile)) }
        )
    }
}

// Side sheet for users to change what is being displayed on the screen
@Composable
fun FilterDraw(
    state: ShiftListState,
    onEvent: (ShiftListEvents) -> Unit
) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .padding(all = 10.dp)
                .verticalScroll(state = rememberScrollState())
        ) {
            Text(text = stringResource(R.string.filters))
            CreateCheckBox(
                checkVal = state.shiftState.shiftsCheck,
                checkName = R.string.shifts,
                onChange = { onEvent(ShiftListEvents.ToggleFilters(R.string.shifts)) })
            CreateCheckBox(
                checkVal = state.shiftState.holidayCheck,
                checkName = R.string.holiday,
                onChange = { onEvent(ShiftListEvents.ToggleFilters(R.string.holiday)) })
            CreateCheckBox(
                checkVal = state.shiftState.unavailabilityCheck,
                checkName = R.string.unavailability,
                onChange = { onEvent(ShiftListEvents.ToggleFilters(R.string.unavailability)) })

//            TODO FUTURE - Future enhancement
//            Spacer(modifier = Modifier.height(height = 10.dp))
//            Text(text = "Admin")
//            CreateCheckBox(
//                checkVal = state.onlyAdminShifts,
//                checkName = R.string.onlyAdminShifts,
//                onChange = { onEvent(ShiftListEvents.ToggleFilters(R.string.onlyAdminShifts)) })
        }
    }
}

@Composable
fun CreateCheckBox(
    checkVal: Boolean,
    checkName: Int,
    onChange: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checkVal, onCheckedChange = { onChange() })
        Text(text = stringResource(checkName))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewShiftEventDialogWindow(
    state: ShiftListState,
    onEvent: (ShiftListEvents) -> Unit
) {
    if (state.showNewShiftEventDialog) {
        Dialog(onDismissRequest = { onEvent(ShiftListEvents.ToggleNewMainScreenEventsDialog) }) {
            BackgroundOutlineWithButtons(
                confirmButton = { onEvent(ShiftListEvents.ConfirmNewMainScreenEventsDialog) },
                cancelButton = { onEvent(ShiftListEvents.CancelNewMainScreenEventsDialog) }
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 10.dp),
                    modifier = Modifier.padding(all = 5.dp)
                ) {
                    Spacer(modifier = Modifier.height(height = 10.dp))

                    LabelledTextDropDownFields(
                        currentValue = stringResource(state.requestType.stringVal),
                        placeHolderText = stringResource(R.string.request_type),
                        expandedDropDown = state.expandRequestTypeDropDown,
                        dropDownToggle = { onEvent(ShiftListEvents.ToggleRequestTypeDropDown) }
                    ) {
                        DropdownMenu(
                            expanded = state.expandRequestTypeDropDown,
                            onDismissRequest = { onEvent(ShiftListEvents.ToggleRequestTypeDropDown) }
                        ) {
                            for (type in ShiftEventTypes.entries) {
                                if (type == ShiftEventTypes.SHIFTS && !state.isAdmin) continue
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(type.stringVal)) },
                                    onClick = {
                                        onEvent(
                                            ShiftListEvents.SetRequestType(
                                                requestType = type
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }

                    DateSelectorField(
                        currentValue = state.datePickerHeadlineVal,
                        placeHolderText = stringResource(R.string.date_range),
                        toggleDatePicker = { onEvent(ShiftListEvents.ToggleDateSelectorDropDown) },
                        dateError = state.datePickerError,
                        modifier = Modifier
                            .width(width = 400.dp)
                            .height(height = 80.dp)
                    ) {
                        DateTimeRangeSelectorDropDown(state = state, onEvent = onEvent)
                    }

                    if (state.requestType == ShiftEventTypes.SHIFTS && state.isAdmin) {
                        LabelledTextDropDownFields(
                            currentValue = state.selectedPeopleString,
                            placeHolderText = stringResource(R.string.employees),
                            expandedDropDown = state.selectedPeopleExpanded,
                            dropDownToggle = { onEvent(ShiftListEvents.ToggleSelectedPeopleDropDown) }
                        ) {
                            DropdownMenu(
                                expanded = state.selectedPeopleExpanded,
                                onDismissRequest = { onEvent(ShiftListEvents.ToggleSelectedPeopleDropDown) }
                            ) {
                                for (person in state.shiftState.people) {
                                    if (person.employeeUid !in state.selectedPeople) {
                                        DropdownMenuItem(
                                            text = { Text(text = person.name) },
                                            onClick = { onEvent(ShiftListEvents.AddEmployee(person = person.employeeUid)) }
                                        )
                                    }
                                }
                            }
                        }

                        LabelledTextInputFields(
                            currentValue = state.role,
                            placeHolderText = stringResource(R.string.role),
                            onValueChange = { onEvent(ShiftListEvents.SetRole(role = it)) },
                            textFieldError = state.roleError,
                            errorString = stringResource(R.string.roleError),
                            testingTag = TestTagCompanyInformationRole
                        )

                        LabelledTextInputFields(
                            currentValue = state.location,
                            placeHolderText = stringResource(R.string.location),
                            onValueChange = { onEvent(ShiftListEvents.SetLocation(location = it)) },
                            textFieldError = state.locationError,
                            errorString = stringResource(R.string.locationError),
                            testingTag = TestTagShiftLocation
                        )
                    }

                    TextField(
                        value = state.newEventNotes,
                        placeholder = { Text(text = stringResource(R.string.notes)) },
                        onValueChange = { onEvent(ShiftListEvents.UpdateNotes(notes = it)) },
                        maxLines = 5,
                        modifier = Modifier
                            .width(width = 400.dp)
                            .height(height = 80.dp)
                    )

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeRangeSelectorDropDown(
    state: ShiftListState,
    onEvent: (ShiftListEvents) -> Unit
) {
    val dateState = rememberDateRangePickerState()


    if (state.showDateTimeRangePickers) {
        Popup(
            onDismissRequest = { onEvent(ShiftListEvents.ToggleDateSelectorDropDown) },
            alignment = Alignment.Center
        ) {
            Card {

                // Selecting the date range the user is interested in submitting their request for
                if (state.dateTimePickerState == 0) {
                    BackgroundOutlineWithButtons(
                        confirmButton = {
                            onEvent(
                                ShiftListEvents.ConfirmRangeDateSelector(
                                    startDate = formatDateToString(dateLong = dateState.selectedStartDateMillis),
                                    endDate = formatDateToString(dateLong = dateState.selectedEndDateMillis)
                                )
                            )
                        },
                        cancelButton = { onEvent(ShiftListEvents.CancelDateSelectorDialog) }
                    ) {
                        DateRangePicker(
                            state = dateState,
                            title = {}, // Want the title to be left blank but has a default value if not specified
                            headline = { Text(text = state.datePickerHeadlineVal) }
                        )
                    }
                }

                // Selecting start time user submitted request
                if (state.dateTimePickerState == 1) {
                    val startTimePickerState = TimePickerState(
                        initialHour = state.timeRequestStartHour,
                        initialMinute = state.timeRequestStartMinute,
                        is24Hour = true
                    )

                    BackgroundOutlineWithButtons(
                        confirmButton = {
                            onEvent(
                                ShiftListEvents.ConfirmStartTimeSelector(
                                    hour = startTimePickerState.hour,
                                    minute = startTimePickerState.minute
                                )
                            )
                        },
                        cancelButton = { onEvent(ShiftListEvents.CancelDateSelectorDialog) }
                    ) {
                        TimeDialPicker(
                            title = stringResource(R.string.start_time),
                            timeState = startTimePickerState
                        )
                    }
                }

                // Selecting end time user submitted request
                if (state.dateTimePickerState == 2) {
                    val endTimePickerState = TimePickerState(
                        initialHour = state.timeRequestEndHour,
                        initialMinute = state.timeRequestEndMinute,
                        is24Hour = true
                    )
                    BackgroundOutlineWithButtons(
                        confirmButton = {
                            onEvent(
                                ShiftListEvents.ConfirmEndTimeSelector(
                                    hour = endTimePickerState.hour,
                                    minute = endTimePickerState.minute
                                )
                            )
                        },
                        cancelButton = { onEvent(ShiftListEvents.CancelDateSelectorDialog) }
                    ) {
                        TimeDialPicker(
                            title = stringResource(R.string.end_time),
                            timeState = endTimePickerState
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDialPicker(
    title: String,
    timeState: TimePickerState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        TimePicker(state = timeState)
    }
}

/*
    Previews ---------------------------
 */

@ViewingSystemThemes
@Composable
fun PreviewShiftListScreen() {
    JustInTimeTheme {
        ShiftListScreen(
            state = ShiftListState(),
            onEvent = {},
            calendarEvents = {},
            shiftEvents = {})
    }
}

@ViewingSystemThemes
@Composable
fun PreviewNewShiftEvent() {
    JustInTimeTheme {
        NewShiftEventDialogWindow(
            state = ShiftListState(),
            onEvent = {}
        )
    }
}

@ViewingSystemThemes
@Composable
fun PreviewNewShiftEvent_DatePicker() {
    JustInTimeTheme {
        NewShiftEventDialogWindow(
            state = ShiftListState(showDateTimeRangePickers = true),
            onEvent = {}
        )
    }
}

@ViewingSystemThemes
@Composable
fun PreviewNewShiftEvent_TimePicker() {
    JustInTimeTheme {
        NewShiftEventDialogWindow(
            state = ShiftListState(
                showDateTimeRangePickers = true,
                dateTimePickerState = 1
            ),
            onEvent = {}
        )
    }
}