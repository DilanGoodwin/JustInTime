package com.dbad.justintime.f_login_register.presentation.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.dbad.justintime.R

@Composable
fun TextInputField(
    currentValue: String,
    placeHolderText: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = currentValue,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(text = placeHolderText) },
        modifier = Modifier
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
    onValueChange: (String) -> Unit,
    visiblePassword: () -> Unit
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
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .width(400.dp)
            .height(60.dp)
    )
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