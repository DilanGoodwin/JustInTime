package com.dbad.justintime.core.presentation.util

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.dbad.justintime.R
import com.dbad.justintime.f_local_db.domain.model.util.PreferredContactMethod
import com.dbad.justintime.f_local_db.domain.model.util.Relation
import com.dbad.justintime.ui.theme.JustInTimeTheme
import java.util.Calendar

@Composable
fun TextInputField(
    currentValue: String,
    placeHolderText: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    textFieldError: Boolean = false,
    errorString: String = "",
    testingTag: String = ""
) {
    TextField(
        value = currentValue,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(text = placeHolderText) },
        isError = textFieldError,
        supportingText = {
            if (textFieldError) {
                Text(
                    text = errorString,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .width(400.dp)
            .height(80.dp)
            .testTag(tag = testingTag)
    )
}

@Composable
fun LabelledTextInputFields(
    modifier: Modifier = Modifier,
    currentValue: String,
    placeHolderText: String,
    onValueChange: (String) -> Unit,
    textFieldError: Boolean = false,
    errorString: String = "",
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    testingTag: String = ""
) {
    TextField(
        value = currentValue,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(text = placeHolderText) },
        label = { Text(text = placeHolderText) },
        isError = textFieldError,
        supportingText = {
            if (textFieldError) {
                Text(
                    text = errorString,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .width(400.dp)
            .height(80.dp)
            .testTag(tag = testingTag)
    )
}

@Composable
fun LabelledTextDropDownFields(
    modifier: Modifier = Modifier,
    currentValue: String,
    placeHolderText: String,
    testTag: String = "",
    readOnly: Boolean = false,
    expandedDropDown: Boolean,
    dropDownToggle: () -> Unit,
    menu: @Composable () -> Unit
) {
    TextField(
        value = currentValue,
        onValueChange = {},
        placeholder = { Text(text = placeHolderText) },
        label = { Text(text = placeHolderText) },
        trailingIcon = {
            if (!readOnly) {
                IconButton(
                    onClick = { dropDownToggle() },
                    modifier = Modifier.testTag(tag = testTag)
                ) {
                    Icon(
                        imageVector = if (expandedDropDown) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "", //TODO add content description to string xml
                    )

                    // Display specific menu dropdown content
                    menu()
                }
            }
        },
        readOnly = true,
        singleLine = true,
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .width(400.dp)
            .height(60.dp)
    )
}

@Composable
fun PasswordField(
    currentValue: String,
    placeHolderText: String,
    showPassword: Boolean,
    textFieldError: Boolean,
    errorString: String,
    onValueChange: (String) -> Unit,
    visiblePassword: () -> Unit,
    testingTag: String
) {
    TextField(
        value = currentValue,
        placeholder = { Box(modifier = Modifier.fillMaxSize()) { Text(text = placeHolderText) } },
        trailingIcon = {
            IconButton(
                onClick = { visiblePassword() }
            ) {
                if (showPassword) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(R.string.fieldVisibilityOff)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = stringResource(R.string.fieldVisibility)
                    )
                }
            }
        },
        onValueChange = { onValueChange(it) },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = textFieldError,
        supportingText = {
            if (textFieldError) {
                Text(
                    text = errorString,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        singleLine = true,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .width(400.dp)
            .height(80.dp)
            .testTag(tag = testingTag)
    )
}

@Composable
fun PreferredContactField(
    currentValue: String,
    expandDropDown: Boolean,
    dropDownToggle: () -> Unit,
    selectContactMethod: (PreferredContactMethod) -> Unit
) {
    DropDownField(
        currentValue = currentValue,
        placeHolderText = stringResource(R.string.prefContactMethod),
        fieldTestingTag = TestTagMainPreferredContactMethodField,
        dropDownToggleTestingTag = TestTagPreferredContactMethodField,
        expandedDropDown = expandDropDown,
        dropDownToggle = dropDownToggle,
        modifier = Modifier
            .width(width = 400.dp)
            .height(height = 60.dp)
    ) {
        DropdownMenu(expanded = expandDropDown, onDismissRequest = { dropDownToggle() }) {
            for (method in PreferredContactMethod.entries) {
                if (method != PreferredContactMethod.NONE) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(method.stringVal)) },
                        onClick = {
                            selectContactMethod(method)
                            dropDownToggle()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RelationField(
    currentValue: String,
    expandDropDown: Boolean,
    dropDownToggle: () -> Unit,
    selectRelation: (Relation) -> Unit
) {
    DropDownField(
        currentValue = currentValue,
        placeHolderText = stringResource(R.string.relation),
        fieldTestingTag = TestTagMainEmergencyContactRelationField,
        dropDownToggleTestingTag = TestTagEmergencyContactRelation,
        expandedDropDown = expandDropDown,
        dropDownToggle = dropDownToggle,
        modifier = Modifier
            .width(width = 400.dp)
            .height(height = 60.dp)
    ) {
        DropdownMenu(expanded = expandDropDown, onDismissRequest = { dropDownToggle() }) {
            for (rel in Relation.entries) {
                if (rel != Relation.NONE) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(rel.stringVal)) },
                        onClick = {
                            selectRelation(rel)
                            dropDownToggle()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DropDownField(
    modifier: Modifier = Modifier,
    currentValue: String,
    placeHolderText: String,
    fieldTestingTag: String = "",
    dropDownToggleTestingTag: String,
    expandedDropDown: Boolean,
    dropDownToggle: () -> Unit,
    menu: @Composable () -> Unit
) {
    TextField(
        value = currentValue,
        onValueChange = {},
        placeholder = { Text(text = placeHolderText) },
        trailingIcon = {
            IconButton(
                onClick = { dropDownToggle() },
                modifier = Modifier.testTag(tag = dropDownToggleTestingTag)
            ) {
                Icon(
                    imageVector = if (expandedDropDown) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "", //TODO add content description to string xml
                )

                // Display specific menu dropdown content
                menu()
            }
        },
        readOnly = true,
        singleLine = true,
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .testTag(tag = fieldTestingTag)
    )
}

@Composable
fun DateSelectorField(
    currentValue: String,
    placeHolderText: String,
    toggleDatePicker: () -> Unit,
    dateError: Boolean,
    modifier: Modifier = Modifier,
    datePicker: @Composable () -> Unit,
) {
    TextField(
        value = currentValue,
        onValueChange = {},
        placeholder = {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = placeHolderText)
            }
        },
        trailingIcon = {
            IconButton(
                onClick = { toggleDatePicker() },
                modifier = Modifier.testTag(tag = TestTagDateOfBirthField)
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "")
                //TODO - provide content description
                datePicker()
            }
        },
        isError = dateError,
        supportingText = {
            if (dateError) {
                Text(
                    text = stringResource(R.string.dobError),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        readOnly = true,
        singleLine = true,
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectorDropDown(
    showDatePicker: Boolean,
    saveSelectedDate: (String) -> Unit
) {
    val dateState = rememberDatePickerState()
    if (showDatePicker) {
        Popup(
            onDismissRequest = {
                val saveDate =
                    if (dateState.selectedDateMillis == null) 0 else dateState.selectedDateMillis
                saveSelectedDate(formatDateToString(saveDate!!))
            },
            alignment = Alignment.Center
        ) {
            Box {
                DatePicker(
                    state = dateState,
                    showModeToggle = false,
                    modifier = Modifier.testTag(tag = TestTagDatePickerPopUp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelectionField(
    currentValue: String,
    placeHolderText: String,
    showTimePicker: Boolean,
    timeError: Boolean,
    toggleTimePicker: () -> Unit,
    timePickerState: TimePickerState,
    modifier: Modifier = Modifier
) {
    TextField(
        value = currentValue,
        onValueChange = {},
        placeholder = {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = placeHolderText)
            }
        },
        trailingIcon = {
            IconButton(
                onClick = { toggleTimePicker() },
                modifier = Modifier.testTag(tag = "") //TODO
            ) {
                Icon(imageVector = Icons.Default.AccessTime, contentDescription = "") //TODO
                TimeDialPicker(
                    timePickerState = timePickerState,
                    showTimePicker = showTimePicker,
                    saveSelectedTime = toggleTimePicker
                )
            }
        },
        isError = timeError,
        supportingText = {
            if (timeError) {
                Text(
                    text = "", //TODO
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        readOnly = true,
        singleLine = true,
        modifier = modifier.clip(shape = RoundedCornerShape(size = 8.dp))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDialPicker(
    timePickerState: TimePickerState,
    showTimePicker: Boolean,
    saveSelectedTime: () -> Unit
) {
    if (showTimePicker) {
        Popup(
            onDismissRequest = {}
        ) {
            Column(modifier = Modifier.padding(all = 10.dp)) {
                TimePicker(state = timePickerState)
                Button(
                    onClick = { saveSelectedTime() },
                    modifier = Modifier.align(alignment = Alignment.End)
                ) {
                    Text(text = stringResource(R.string.save))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ViewingSystemThemes
@Composable
fun TimeDialPickerViewer() {
    val currentTime = Calendar.getInstance()
    JustInTimeTheme {
        TimeDialPicker(
            timePickerState = TimePickerState(
                initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
                initialMinute = currentTime.get(Calendar.MINUTE),
                is24Hour = true
            ),
            showTimePicker = true,
            saveSelectedTime = {}
        )
    }
}

@Composable
fun ExpandableCardArea(
    isExpanded: Boolean,
    expandableButtonClick: () -> Unit,
    cardTitle: String,
    testTag: String,
    content: @Composable () -> Unit
) {
    val rotationState by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)

    Card(
        shape = RoundedCornerShape(size = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        ),
        onClick = { expandableButtonClick() },
        modifier = Modifier
            .width(500.dp)
            .testTag(tag = testTag)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = cardTitle,
                    modifier = Modifier
                        .weight(weight = 9f)
                        .padding(15.dp)
                )
                IconButton(onClick = { expandableButtonClick() }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "", //TODO add content description to strings xml
                        modifier = Modifier
                            .weight(weight = 1f)
                            .rotate(rotationState)
                    )
                }
            }

            if (isExpanded) {
                Column(
                    modifier = Modifier
                        .padding(all = 20.dp)
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    content()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackgroundOutlineWithButtons(
    confirmButton: () -> Unit,
    cancelButton: () -> Unit,
    content: @Composable () -> Unit
) {
    /*
    We are utilising the DatePickerDialog here as it creates its own outline area that we can
    just place other items within. This prevents us from needing to create an entire card view
    structure every single time we want to display something that does not have its own backing.

    Currently wrapped within a secondary function as this is an experimental feature that may
    be removed in a future release of the Material3 API. Wrapping in a secondary function allows
    for all instances of the function to be updated at once should the API implementation change
    affecting the application.
     */
    DatePickerDialog(
        onDismissRequest = { cancelButton() },
        confirmButton = { TextButton(onClick = { confirmButton() }) { Text(text = "Confirm") } },
        dismissButton = { TextButton(onClick = { cancelButton() }) { Text(text = "Cancel") } }
    ) {
        content()
    }
}

@Composable
fun DualButtonFields(
    leftButtonValue: String,
    leftButtonOnClick: () -> Unit,
    rightButtonValue: String,
    rightButtonOnClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            FormatButtons(buttonValue = leftButtonValue, onClick = leftButtonOnClick)
            Spacer(modifier = Modifier.width(20.dp))
            FormatButtons(buttonValue = rightButtonValue, onClick = rightButtonOnClick)
        }
    }
}

@Composable
private fun FormatButtons(buttonValue: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .width(120.dp)
            .height(60.dp)
    ) {
        Text(text = buttonValue)
    }
}