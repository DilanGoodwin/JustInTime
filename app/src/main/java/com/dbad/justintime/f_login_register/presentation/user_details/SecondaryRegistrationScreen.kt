package com.dbad.justintime.f_login_register.presentation.user_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.TestTagNameField
import com.dbad.justintime.core.presentation.util.TestTagPhoneNumberField
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.f_login_register.presentation.util.EmergencyContactField
import com.dbad.justintime.f_login_register.presentation.util.JustInTimeLogoDisplay
import com.dbad.justintime.f_login_register.presentation.util.PreferredContactField
import com.dbad.justintime.f_login_register.presentation.util.TextInputField
import com.dbad.justintime.ui.theme.JustInTimeTheme

@Composable
fun ExtraRegistrationDetails(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.TopStart, modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                JustInTimeLogoDisplay()
            }

            // User Text Input Area
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp)) {
                    TextInputField(
                        currentValue = "",
                        placeHolderText = stringResource(R.string.name),
                        onValueChange = {},
                        textFieldError = false,
                        errorString = stringResource(R.string.noNameProvided),
                        testingTag = TestTagNameField
                    )

                    TextInputField(
                        currentValue = "",
                        placeHolderText = stringResource(R.string.preferredName),
                        onValueChange = {}
                    )

                    TextInputField(
                        currentValue = "",
                        placeHolderText = stringResource(R.string.phoneNumb),
                        onValueChange = {},
                        textFieldError = false,
                        errorString = stringResource(R.string.invalidPhoneNumb),
                        testingTag = TestTagPhoneNumberField
                    )

                    PreferredContactField(
                        currentValue = "",
                        onValueChange = {},
                        expandDropDown = false,
                        dropDownDismissEvent = {}
                    )

                    EmergencyContactField(isExpanded = true)
                }
            }
        }
    }
}

@ViewingSystemThemes
@Composable
fun ExtraRegisterScreenPreview() {
    JustInTimeTheme { ExtraRegistrationDetails() }
}