package com.dbad.justintime.f_login_register.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.DualButtonFields
import com.dbad.justintime.f_login_register.presentation.util.JustInTimeLogoDisplay
import com.dbad.justintime.core.presentation.util.PasswordField
import com.dbad.justintime.core.presentation.util.TestTagEmailField
import com.dbad.justintime.core.presentation.util.TestTagPasswordField
import com.dbad.justintime.core.presentation.util.TextInputField
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.ui.theme.JustInTimeTheme

// Stateful
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onRegistration: () -> Unit,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    val event = viewModel::onEvent
    event(LoginEvent.SetRegistrationAction(onRegistration))
    event(LoginEvent.SetLoginAction(onLogin))

    LoginScreen(state = state, onEvent = event, modifier = modifier)
}

@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.TopStart, modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                JustInTimeLogoDisplay()
            }
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp)) {

                    TextInputField(
                        currentValue = state.email,
                        onValueChange = { onEvent(LoginEvent.SetEmail(email = it)) },
                        placeHolderText = stringResource(R.string.email),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        textFieldError = state.showError,
                        errorString = stringResource(R.string.emailOrPasswordError),
                        testingTag = TestTagEmailField
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    PasswordField(
                        currentValue = state.password,
                        placeHolderText = stringResource(R.string.password),
                        showPassword = state.showPassword,
                        onValueChange = { onEvent(LoginEvent.SetPassword(password = it)) },
                        visiblePassword = { onEvent(LoginEvent.ToggleViewPassword) },
                        textFieldError = state.showError,
                        errorString = stringResource(R.string.emailOrPasswordError),
                        testingTag = TestTagPasswordField
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            DualButtonFields(
                leftButtonValue = stringResource(R.string.register),
                leftButtonOnClick = { onEvent(LoginEvent.RegisterUser) },
                rightButtonValue = stringResource(R.string.login),
                rightButtonOnClick = { onEvent(LoginEvent.LoginUser) }
            )
        }
    }
}

@ViewingSystemThemes
@Composable
fun LoginScreenPreview() {
    JustInTimeTheme { LoginScreen(state = LoginState(), onEvent = {}) }
}