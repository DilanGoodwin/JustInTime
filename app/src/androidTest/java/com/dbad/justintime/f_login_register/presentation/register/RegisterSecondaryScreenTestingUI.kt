package com.dbad.justintime.f_login_register.presentation.register

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterSecondaryScreenTestingUI {

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun reset() = runTest {
        testRule.setContent {}
    }

    @Test
    fun checkRegistrationSecondaryScreenValuesDisplayed() = runTest {}

    @Test
    fun checkPhoneNumberErrors() = runTest {}

    @Test
    fun checkPreferredContactMethodOptions() = runTest {}
}