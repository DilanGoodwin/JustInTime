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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.dbad.justintime.f_login_register.domain.model.util.PreferredContactMethod
import com.dbad.justintime.f_login_register.domain.model.util.Relation

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
    TextField(
        value = currentValue,
        onValueChange = {},
        placeholder = { Text(text = stringResource(R.string.prefContactMethod)) },
        trailingIcon = {
            IconButton(
                onClick = { dropDownToggle() },
                modifier = Modifier.testTag(tag = TestTagPreferredContactMethodField)
            ) {
                Icon(
                    imageVector = if (expandDropDown) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "", //TODO add content description to string xml
                )
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
        },
        readOnly = true,
        singleLine = true,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .width(400.dp)
            .height(60.dp)
    )
}

@Composable
fun RelationField(
    currentValue: String,
    expandDropDown: Boolean,
    dropDownToggle: () -> Unit,
    selectRelation: (Relation) -> Unit
) {
    TextField(
        value = currentValue,
        onValueChange = {},
        placeholder = { Text(text = stringResource(R.string.relation)) },
        trailingIcon = {
            IconButton(
                onClick = { dropDownToggle() },
                modifier = Modifier.testTag(tag = TestTagEmergencyContactRelation)
            ) {
                Icon(
                    imageVector = if (expandDropDown) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "", //TODO add content description to string xml
                )
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
        },
        readOnly = true,
        singleLine = true,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .width(400.dp)
            .height(60.dp)
    )
}

@Composable
fun DateSelectorField(
    currentValue: String,
    placeHolderText: String,
    showDatePicker: Boolean,
    toggleDatePicker: () -> Unit,
    dateError: Boolean,
    saveSelectedDate: (String) -> Unit
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
                DateSelectorDropDown(
                    showDatePicker = showDatePicker,
                    saveSelectedDate = saveSelectedDate
                )
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
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .width(400.dp)
            .height(80.dp)
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
            .width(400.dp)
            .testTag(tag = testTag)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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