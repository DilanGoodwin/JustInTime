package com.dbad.justintime.f_shifts.presentation.shifts_list

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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.core.presentation.util.formatDateToString
import com.dbad.justintime.f_shifts.domain.model.ShiftEventTypes
import com.dbad.justintime.f_shifts.presentation.calendar.CalendarView
import com.dbad.justintime.f_shifts.presentation.individual_shifts.ShiftListView
import com.dbad.justintime.ui.theme.JustInTimeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Stateful
@Composable
fun ShiftListScreen(
    viewModel: CalendarMainScreenViewModel,
    onNavProfile: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val event = viewModel::onEvent
    event(CalendarMainScreenEvent.SetProfileNavMainScreenEvent(onNavProfile))

    ShiftListScreen(state = state, onEvent = event)
}

// Stateless
@Composable
fun ShiftListScreen(
    state: CalendarMainScreenState,
    onEvent: (CalendarMainScreenEvent) -> Unit
) {
    /*
    This is so that the side draw can be opened and closed with an animation, when trying to do this
    within the ViewModelScope the application crashes as the viewModel is attempting to change the
    front-end without a recompilation being triggered.
     */
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = { FilterDraw(state = state, onEvent = onEvent) },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = { ShiftTopAppBar(scope = scope, drawerState = drawerState) },
            bottomBar = { ShiftBottomNavBar(state = state) },
            floatingActionButton = {
                SmallFloatingActionButton(onClick = { onEvent(CalendarMainScreenEvent.ToggleNewMainScreenEventDialog) }) { //TODO
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "")//TODO
                    NewShiftEventDialogWindow(state = state, onEvent = onEvent)
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                CalendarView()
                Spacer(modifier = Modifier.height(height = 10.dp))
                ShiftListView()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftTopAppBar(
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.calendar)) },
        navigationIcon = {
            // Side Menu for filters
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "") //TODO
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ShiftBottomNavBar(state: CalendarMainScreenState) {
    NavigationBar {
        // Calendar Page
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(imageVector = Icons.Filled.DateRange, contentDescription = "") }, //TODO
            label = { Text(text = "Calendar") } //TODO
        )

        // Profile Page
        NavigationBarItem(
            selected = false,
            onClick = { state.navProfilePage() },
            icon = { Icon(imageVector = Icons.Filled.Approval, contentDescription = "") }, //TODO
            label = { Text(text = stringResource(R.string.profile)) }
        )
    }
}

// Side sheet for users to change what is being displayed on the screen
@Composable
fun FilterDraw(
    state: CalendarMainScreenState,
    onEvent: (CalendarMainScreenEvent) -> Unit
) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .padding(all = 10.dp)
                .verticalScroll(state = rememberScrollState())
        ) {
            Text(text = stringResource(R.string.filters))
            CreateCheckBox(
                checkVal = state.shiftsCheck,
                checkName = R.string.shifts,
                onChange = { onEvent(CalendarMainScreenEvent.ToggleFilters(R.string.shifts)) })
            CreateCheckBox(
                checkVal = state.holidayCheck,
                checkName = R.string.holiday,
                onChange = { onEvent(CalendarMainScreenEvent.ToggleFilters(R.string.holiday)) })
            CreateCheckBox(
                checkVal = state.unavailabilityCheck,
                checkName = R.string.unavailability,
                onChange = { onEvent(CalendarMainScreenEvent.ToggleFilters(R.string.unavailability)) })
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
    state: CalendarMainScreenState,
    onEvent: (CalendarMainScreenEvent) -> Unit
) {
    if (state.showNewShiftEventDialog) {
        Dialog(onDismissRequest = { onEvent(CalendarMainScreenEvent.ToggleNewMainScreenEventDialog) }) {
            BackgroundOutlineWithButtons(
                confirmButton = { onEvent(CalendarMainScreenEvent.ConfirmNewMainScreenEventDialog) },
                cancelButton = { onEvent(CalendarMainScreenEvent.CancelNewMainScreenEventDialog) }
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 5.dp),
                    modifier = Modifier.padding(all = 5.dp)
                ) {
                    Spacer(modifier = Modifier.height(height = 10.dp))

                    LabelledTextDropDownFields(
                        currentValue = stringResource(state.requestType.stringVal),
                        placeHolderText = stringResource(R.string.request_type),
                        expandedDropDown = state.expandRequestTypeDropDown,
                        dropDownToggle = { onEvent(CalendarMainScreenEvent.ToggleRequestTypeDropDown) }
                    ) {
                        DropdownMenu(
                            expanded = state.expandRequestTypeDropDown,
                            onDismissRequest = { onEvent(CalendarMainScreenEvent.ToggleRequestTypeDropDown) }
                        ) {
                            for (type in ShiftEventTypes.entries) {
                                if (type == ShiftEventTypes.SHIFTS) continue
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(type.stringVal)) },
                                    onClick = {
                                        onEvent(
                                            CalendarMainScreenEvent.SetRequestType(
                                                requestType = type
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(height = 10.dp))

                    DateSelectorField(
                        currentValue = state.datePickerHeadlineVal,
                        placeHolderText = stringResource(R.string.date_range),
                        toggleDatePicker = { onEvent(CalendarMainScreenEvent.ToggleDateSelectorDropDown) },
                        dateError = state.datePickerError,
                        modifier = Modifier
                            .width(width = 400.dp)
                            .height(height = 80.dp)
                    ) {
                        DateTimeRangeSelectorDropDown(state = state, onEvent = onEvent)
                    }

                    TextField(
                        value = state.newEventNotes,
                        placeholder = { Text(text = stringResource(R.string.notes)) },
                        onValueChange = { onEvent(CalendarMainScreenEvent.UpdateNotes(notes = it)) },
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
    state: CalendarMainScreenState,
    onEvent: (CalendarMainScreenEvent) -> Unit
) {
    val dateState = rememberDateRangePickerState()


    if (state.showDateTimeRangePickers) {
        Popup(
            onDismissRequest = { onEvent(CalendarMainScreenEvent.ToggleDateSelectorDropDown) },
            alignment = Alignment.Center
        ) {
            Card {

                // Selecting the date range the user is interested in submitting their request for
                if (state.dateTimePickerState == 0) {
                    BackgroundOutlineWithButtons(
                        confirmButton = {
                            onEvent(
                                CalendarMainScreenEvent.ConfirmRangeDateSelector(
                                    startDate = formatDateToString(dateLong = dateState.selectedStartDateMillis),
                                    endDate = formatDateToString(dateLong = dateState.selectedEndDateMillis)
                                )
                            )
                        },
                        cancelButton = { onEvent(CalendarMainScreenEvent.CancelDateSelectorDialog) }
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
                                CalendarMainScreenEvent.ConfirmStartTimeSelector(
                                    hour = startTimePickerState.hour,
                                    minute = startTimePickerState.minute
                                )
                            )
                        },
                        cancelButton = { onEvent(CalendarMainScreenEvent.CancelDateSelectorDialog) }
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
                                CalendarMainScreenEvent.ConfirmEndTimeSelector(
                                    hour = endTimePickerState.hour,
                                    minute = endTimePickerState.minute
                                )
                            )
                        },
                        cancelButton = { onEvent(CalendarMainScreenEvent.CancelDateSelectorDialog) }
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
    JustInTimeTheme { ShiftListScreen(state = CalendarMainScreenState(), onEvent = {}) }
}

@ViewingSystemThemes
@Composable
fun PreviewNewShiftEvent() {
    JustInTimeTheme {
        NewShiftEventDialogWindow(
            state = CalendarMainScreenState(),
            onEvent = {}
        )
    }
}

@ViewingSystemThemes
@Composable
fun PreviewNewShiftEvent_DatePicker() {
    JustInTimeTheme {
        NewShiftEventDialogWindow(
            state = CalendarMainScreenState(showDateTimeRangePickers = true),
            onEvent = {}
        )
    }
}

@ViewingSystemThemes
@Composable
fun PreviewNewShiftEvent_TimePicker() {
    JustInTimeTheme {
        NewShiftEventDialogWindow(
            state = CalendarMainScreenState(
                showDateTimeRangePickers = true,
                dateTimePickerState = 1
            ),
            onEvent = {}
        )
    }
}