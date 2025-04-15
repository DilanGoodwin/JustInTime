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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.dbad.justintime.f_shifts.domain.model.ShiftEventTypes
import com.dbad.justintime.ui.theme.JustInTimeTheme
import java.util.Calendar

// Stateless
@Composable
fun ShiftListScreen(state: CalendarState) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // TODO move into state

    ModalNavigationDrawer(
        drawerContent = { FilterDraw() },
        drawerState = drawerState,
        gesturesEnabled = false
    ) {
        Scaffold(
            topBar = { ShiftTopAppBar() },
            bottomBar = { ShiftBottomNavBar() },
            floatingActionButton = {
                SmallFloatingActionButton(onClick = {}) { //TODO
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "")//TODO
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                MonthTitleCard()
                CalendarView()
                Spacer(modifier = Modifier.height(height = 10.dp))
                ShiftListView()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftTopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = "Calendar") },
        navigationIcon = {
            // Side Menu for filters
            IconButton(onClick = {}) { //TODO toggle draw open or close value
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "") //TODO
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ShiftBottomNavBar() {
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
            onClick = {}, //TODO
            icon = { Icon(imageVector = Icons.Filled.Approval, contentDescription = "") }, //TODO
            label = { Text(text = stringResource(R.string.profile)) }
        )
    }
}

// Side sheet for users to change what is being displayed on the screen
@Composable
fun FilterDraw() {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .padding(all = 10.dp)
                .verticalScroll(state = rememberScrollState())
        ) {
            Text(text = "Filters") // TODO possible filters should be an enum that we can iterate through
            CreateCheckBox(checkVal = true, checkName = "Shifts") //TODO
            CreateCheckBox(checkVal = true, checkName = "Holiday") //TODO
            CreateCheckBox(checkVal = true, checkName = "Unavailability") //TODO
        }
    }
}

@Composable
fun CreateCheckBox(checkVal: Boolean, checkName: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checkVal, onCheckedChange = {}) //TODO
        Text(text = checkName)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewShiftEventDialogWindow(state: CalendarState) {
    Dialog(onDismissRequest = {}) { //TODO
        Card(
            shape = RoundedCornerShape(size = 8.dp),
            modifier = Modifier
                .width(width = 500.dp)
                .height(height = 300.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 5.dp),
                modifier = Modifier.padding(all = 5.dp)
            ) {
                LabelledTextDropDownFields(
                    currentValue = stringResource(state.requestType.stringVal),
                    placeHolderText = "Request Type",
                    expandedDropDown = state.expandRequestTypeDropDown, //TODO
                    dropDownToggle = {} //TODO
                ) {
                    DropdownMenu(
                        expanded = state.expandRequestTypeDropDown, //TODO
                        onDismissRequest = {} //TODO
                    ) {
                        for (type in ShiftEventTypes.entries) {
                            DropdownMenuItem(
                                text = { Text(text = stringResource(type.stringVal)) },
                                onClick = {} //TODO
                            )
                        }
                    }
                }

                DateSelectorField(
                    currentValue = "", //TODO
                    placeHolderText = "Date Range",
                    toggleDatePicker = {}, //TODO
                    dateError = false, //TODO
                    modifier = Modifier
                        .width(width = 400.dp)
                        .height(height = 80.dp)
                ) {
                    DateTimeRangeSelectorDropDown(state = state)
                }

                TextField(
                    value = "",
                    placeholder = { Text(text = "Notes") }, //TODO
                    onValueChange = {},
                    maxLines = 5,
                    modifier = Modifier
                        .width(width = 400.dp)
                        .height(height = 80.dp)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeRangeSelectorDropDown(state: CalendarState) {
    val dateState = rememberDateRangePickerState()


    if (state.showDateTimeRangePickers) {
        Popup(
            onDismissRequest = {},
            alignment = Alignment.Center
        ) {
            Card() {
                if (state.dateTimePickerState == 0) {
                    BackgroundOutlineWithButtons(
                        confirmButton = {}, //TODO
                        cancelButton = {} //TODO
                    ) {
                        DateRangePicker(
                            state = dateState,
                            title = {},
                            headline = { Text(text = stringResource(state.datePickerHeadlineVal)) }
                        )
                    }
                }

                if (state.dateTimePickerState == 1) {
                    BackgroundOutlineWithButtons(
                        confirmButton = {}, //TODO
                        cancelButton = {} //TODO
                    ) {
                        TimeDialPicker(
                            title = stringResource(R.string.start_time),
                            timeState = TimePickerState( //TODO
                                initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                                initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
                                is24Hour = true
                            )
                        )
                    }
                }

                if (state.dateTimePickerState == 2) {
                    BackgroundOutlineWithButtons(
                        confirmButton = {}, //TODO
                        cancelButton = {} //TODO
                    ) {
                        TimeDialPicker(
                            title = stringResource(R.string.end_time),
                            timeState = TimePickerState( //TODO
                                initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                                initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
                                is24Hour = true
                            )
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

@ViewingSystemThemes
@Composable
fun PreviewShiftListScreen() {
    JustInTimeTheme { ShiftListScreen(state = CalendarState()) }
}

@ViewingSystemThemes
@Composable
fun PreviewNewShiftEvent() {
    JustInTimeTheme { NewShiftEventDialogWindow(state = CalendarState()) }
}

@ViewingSystemThemes
@Composable
fun PreviewNewShiftEvent_DatePicker() {
    JustInTimeTheme { NewShiftEventDialogWindow(state = CalendarState(showDateTimeRangePickers = true)) }
}

@ViewingSystemThemes
@Composable
fun PreviewNewShiftEvent_TimePicker() {
    JustInTimeTheme {
        NewShiftEventDialogWindow(
            state = CalendarState(
                showDateTimeRangePickers = true,
                dateTimePickerState = 1
            )
        )
    }
}