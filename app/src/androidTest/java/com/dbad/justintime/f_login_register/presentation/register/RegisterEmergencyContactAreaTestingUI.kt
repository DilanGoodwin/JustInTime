package com.dbad.justintime.f_login_register.presentation.register

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterEmergencyContactAreaTestingUI {

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun reset() = runTest {
        testRule.setContent {}
    }

    @Test
    fun checkCollapsedEmergencyContactSummaryShown() = runTest {}

    @Test
    fun checkExpandedEmergencyContactValuesDisplayed() = runTest {}

    @Test
    fun checkEmergencyContactPhoneErrors() = runTest {}

    @Test
    fun checkEmergencyContactEmailErrors() = runTest {}

    @Test
    fun checkEmergencyContactPreferredContactMethodOptions() = runTest {}
}