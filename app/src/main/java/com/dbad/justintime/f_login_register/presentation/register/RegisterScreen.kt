package com.dbad.justintime.f_login_register.presentation.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.ViewingSystemThemes
import com.dbad.justintime.f_login_register.presentation.util.DualButtonFields
import com.dbad.justintime.f_login_register.presentation.util.JustInTimeLogoDisplay
import com.dbad.justintime.f_login_register.presentation.util.PasswordField
import com.dbad.justintime.f_login_register.presentation.util.TextInputField
import com.dbad.justintime.ui.theme.JustInTimeTheme

@Composable
fun RegisterScreen() {
    Box(contentAlignment = Alignment.TopStart, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                JustInTimeLogoDisplay()
            }

            // User Text Input Area
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp)) {
                    TextInputField(
                        currentValue = "",
                        placeHolderText = stringResource(R.string.email),
                        onValueChange = {}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    PasswordField(
                        currentValue = "",
                        placeHolderText = stringResource(R.string.password),
                        showPassword = false,
                        onValueChange = {},
                        visiblePassword = {}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    PasswordField(
                        currentValue = "",
                        placeHolderText = stringResource(R.string.rePassword),
                        showPassword = false,
                        onValueChange = {},
                        visiblePassword = {}
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            // User Action Area
            DualButtonFields(
                leftButtonValue = stringResource(R.string.cancel),
                leftButtonOnClick = {},
                rightButtonValue = stringResource(R.string.register),
                rightButtonOnClick = {}
            )
        }
    }
}

@ViewingSystemThemes
@Composable
fun RegisterScreenPreview() {
    JustInTimeTheme { RegisterScreen() }
}