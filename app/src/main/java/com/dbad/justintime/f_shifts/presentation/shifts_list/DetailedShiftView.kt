package com.dbad.justintime.f_shifts.presentation.shifts_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.DateSelectorField
import com.dbad.justintime.core.presentation.util.LabelledTextInputFields
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationRole
import com.dbad.justintime.core.presentation.util.TestTagShiftLocation
import com.dbad.justintime.core.presentation.util.TimeSelectionField
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.ui.theme.JustInTimeTheme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedShiftView(modifier: Modifier = Modifier) {

    if (true) { //TODO
        ModalBottomSheet(
            onDismissRequest = {},
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 20.dp),
                modifier = Modifier.padding(all = 20.dp)
            ) {

                LabelledTextInputFields(
                    currentValue = "", //TODO
                    placeHolderText = stringResource(R.string.role),
                    onValueChange = {}, //TODO
                    readOnly = true, //TODO
                    testingTag = TestTagCompanyInformationRole
                )

                LabelledTextInputFields(
                    currentValue = "", //TODO
                    placeHolderText = stringResource(R.string.location),
                    onValueChange = {}, //TODO
                    readOnly = true, //TODO
                    testingTag = TestTagShiftLocation
                )

                Row(horizontalArrangement = Arrangement.spacedBy(space = 10.dp)) {
                    DateSelectorField(
                        currentValue = "", //TODO
                        placeHolderText = stringResource(R.string.date),
                        showDatePicker = false, //TODO
                        toggleDatePicker = {}, //TODO
                        dateError = false, //TODO
                        saveSelectedDate = {}, //TODO
                        modifier = Modifier
                            .width(width = 195.dp)
                            .height(height = 80.dp)
                    )

                    TimeSelectionField(
                        currentValue = "", //TODO
                        placeHolderText = stringResource(R.string.time),
                        showTimePicker = false, //TODO
                        timeError = false, //TODO
                        toggleTimePicker = {}, //TODO
                        timePickerState = TimePickerState(
                            initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                            initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
                            is24Hour = true
                        ), //TODO
                        modifier = Modifier
                            .width(width = 195.dp)
                            .height(height = 80.dp)
                    )
                }

                // Notes
                TextField(
                    value = "", //TODO
                    label = { Text(text = stringResource(R.string.notes)) },
                    onValueChange = {}, //TODO
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@ViewingSystemThemes
@Composable
fun DetailedShiftViewPreview() {
    JustInTimeTheme { DetailedShiftView() }
}