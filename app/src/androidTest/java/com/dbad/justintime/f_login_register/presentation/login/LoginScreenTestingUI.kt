package com.dbad.justintime.f_login_register.presentation.login

import androidx.compose.ui.test.junit4.createComposeRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTestingUI {

    @get:Rule
    val testRule = createComposeRule()

    @Before
    fun reset() = runTest {
        testRule.setContent { LoginScreen() }
    }

    @Test
    fun checkValuesDisplayed() = runTest {}

    @Test
    fun checkEmailFieldErrors() = runTest {}

    @Test
    fun checkPasswordFieldErrors() = runTest {}

    @Test
    fun checkValidLoginAttempt() = runTest {}

    @Test
    fun checkInvalidLoginAttempt() = runTest {}

    @Test
    fun checkRegisterButton() = runTest {}
}