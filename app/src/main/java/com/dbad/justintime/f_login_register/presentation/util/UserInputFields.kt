package com.dbad.justintime.f_login_register.presentation.util

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.TestTagEmergencyContactExpandableField
import com.dbad.justintime.core.presentation.util.TestTagPreferredContactMethodField
import com.dbad.justintime.f_login_register.domain.model.util.PreferredContactMethod

@Composable
fun TextInputField(
    currentValue: String,
    placeHolderText: String,
    textFieldError: Boolean = false,
    errorString: String = "",
    onValueChange: (String) -> Unit,
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
fun EmergencyContactField(
    isExpanded: Boolean,
    expandableButtonClick: () -> Unit,
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
            .testTag(tag = TestTagEmergencyContactExpandableField)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.emergencyContact),
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