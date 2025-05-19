package com.dbad.justintime.f_shifts.presentation.individual_shifts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.BackgroundOutlineWithButtons
import com.dbad.justintime.core.presentation.util.DateSelectorField
import com.dbad.justintime.core.presentation.util.LabelledTextInputFields
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationRole
import com.dbad.justintime.core.presentation.util.TestTagShiftLocation
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.core.presentation.util.formatDateToString
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes
import com.dbad.justintime.f_shifts.presentation.shifts_list.TimeDialPicker
import com.dbad.justintime.f_shifts.presentation.util.formatTimeString
import com.dbad.justintime.ui.theme.JustInTimeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedShiftView(
    state: ShiftState,
    onEvent: (ShiftEvents) -> Unit,
    modifier: Modifier = Modifier
) {

    // Display Bottom Sheet
    if (state.showSelectedShift) {
        ModalBottomSheet(
            onDismissRequest = { onEvent(ShiftEvents.ToggleSelectedEvent) },
            shape = RoundedCornerShape(20.dp),
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 20.dp),
                modifier = Modifier.padding(all = 20.dp)
            ) {

                if (state.selectedShiftOrEvent.rolePosition.isNotEmpty()) {
                    LabelledTextInputFields(
                        currentValue = state.selectedShiftOrEvent.rolePosition,
                        placeHolderText = stringResource(R.string.role),
                        onValueChange = { onEvent(ShiftEvents.SetRole(role = it)) },
                        readOnly = state.readOnly,
                        testingTag = TestTagCompanyInformationRole
                    )
                }

                if (state.selectedShiftOrEvent.location.isNotEmpty()) {
                    LabelledTextInputFields(
                        currentValue = state.selectedShiftOrEvent.location,
                        placeHolderText = stringResource(R.string.location),
                        onValueChange = { onEvent(ShiftEvents.SetLocation(location = it)) },
                        readOnly = state.readOnly,
                        testingTag = TestTagShiftLocation
                    )
                }


                DateSelectorField(
                    currentValue = "${state.selectedShiftOrEvent.startDate} - ${state.selectedShiftOrEvent.endDate}",
                    placeHolderText = stringResource(R.string.date),
                    toggleDatePicker = { onEvent(ShiftEvents.ToggleDatePicker) },
                    dateError = state.dateError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 80.dp)
                ) {
                    if (state.showDatePicker) {
                        val dateState = rememberDateRangePickerState()

                        BackgroundOutlineWithButtons(
                            confirmButton = {
                                onEvent(
                                    ShiftEvents.SetDate(
                                        startDate = formatDateToString(dateLong = dateState.selectedStartDateMillis),
                                        endDate = formatDateToString(dateLong = dateState.selectedEndDateMillis)
                                    )
                                )
                            },
                            cancelButton = { onEvent(ShiftEvents.ToggleDatePicker) }
                        ) {
                            DateRangePicker(
                                state = dateState,
                                title = {}, // Want the title to be left blank but has a default value if not specified
                                headline = {}
                            )
                        }
                    }
                }

                DateSelectorField(
                    currentValue = if (state.selectedShiftOrEvent.startTime == "0:0" && state.selectedShiftOrEvent.endTime == "0:0") {
                        stringResource(R.string.full_day)
                    } else {
                        "${formatTimeString(state.selectedShiftOrEvent.startTime)} - ${
                            formatTimeString(
                                state.selectedShiftOrEvent.endTime
                            )
                        }"
                    },
                    placeHolderText = stringResource(R.string.time),
                    toggleDatePicker = { onEvent(ShiftEvents.ToggleTimePicker) },
                    dateError = state.timeError,
                    errorMsg = R.string.timeError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 80.dp)
                ) {
                    if (state.showTimePicker) {
                        val timePickerVals = TimePickerState(
                            initialHour = if (state.timePickerState == 0) {
                                state.selectedShiftOrEvent.startTime.split(":")[0].toInt()
                            } else {
                                state.selectedShiftOrEvent.endTime.split(":")[0].toInt()
                            },
                            initialMinute = if (state.timePickerState == 0) {
                                state.selectedShiftOrEvent.startTime.split(":")[1].toInt()
                            } else {
                                state.selectedShiftOrEvent.endTime.split(":")[1].toInt()
                            },
                            is24Hour = true
                        )

                        BackgroundOutlineWithButtons(
                            confirmButton = {
                                onEvent(
                                    ShiftEvents.SetTime(
                                        hour = timePickerVals.hour,
                                        minute = timePickerVals.minute
                                    )
                                )
                            },
                            cancelButton = { onEvent(ShiftEvents.ToggleTimePicker) }
                        ) {
                            TimeDialPicker(
                                title = stringResource(if (state.timePickerState == 0) R.string.start_time else R.string.end_time),
                                timeState = timePickerVals
                            )
                        }
                    }

                }

                // Notes
                TextField(
                    value = state.selectedShiftOrEvent.notes,
                    label = { Text(text = stringResource(R.string.notes)) },
                    onValueChange = { onEvent(ShiftEvents.SetNotes(notes = it)) },
                    readOnly = state.readOnly,
                    modifier = Modifier.fillMaxWidth()
                )

                // Is user admin, allow editing
                if (state.isAdmin) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (state.selectedShiftOrEvent.type == ShiftEventTypes.SHIFTS) {
                            Button(onClick = { onEvent(ShiftEvents.RemoveEvent) }) {
                                Text(text = stringResource(R.string.remove))
                            }

                            Spacer(modifier = Modifier.width(width = 5.dp))

                            Button(onClick = { onEvent(ShiftEvents.ToggleEditMode) }) {
                                Text(
                                    text = if (state.readOnly) stringResource(R.string.edit) else stringResource(
                                        R.string.save
                                    )
                                )
                            }
                        } else if (!state.selectedShiftOrEvent.approved) {
                            Button(onClick = { onEvent(ShiftEvents.RemoveEvent) }) {
                                Text(text = stringResource(R.string.deny))
                            }

                            Spacer(modifier = Modifier.width(width = 5.dp))

                            Button(onClick = { onEvent(ShiftEvents.ApproveEvent) }) {
                                Text(text = stringResource(R.string.approve))
                            }
                        }
                    }
                }
            }
        }
    }
}

@ViewingSystemThemes
@Composable
fun DetailedShiftViewPreview() {
    JustInTimeTheme {
        DetailedShiftView(
            state = ShiftState(showSelectedShift = true),
            onEvent = {})
    }
}