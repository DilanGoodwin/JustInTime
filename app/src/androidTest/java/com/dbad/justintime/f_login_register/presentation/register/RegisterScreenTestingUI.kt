package com.dbad.justintime.f_login_register.presentation.register

import androidx.compose.ui.test.junit4.createComposeRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterScreenTestingUI {

    @get:Rule
    val testRule = createComposeRule()

    @Before
    fun reset() = runTest {
        testRule.setContent { RegisterScreen() }
    }

    // Primary Screen

    @Test
    fun checkRegistrationPrimaryScreenValuesDisplayed() = runTest {}

    @Test
    fun checkEmailFieldErrors() = runTest {}

    @Test
    fun checkPasswordFieldErrors() = runTest {}

    @Test
    fun checkReEnterPasswordFieldErrors() = runTest {}

    @Test
    fun checkCancelButton() = runTest {}

    @Test
    fun checkRegisterButton() = runTest {}

    @Test
    fun checkValidRegistrationAttempt() = runTest {}

    @Test
    fun checkInvalidRegistrationAttempt() = runTest {}

    // Secondary Screen

    @Test
    fun checkRegistrationSecondaryScreenValuesDisplayed() = runTest {}

    @Test
    fun checkPhoneNumberErrors() = runTest {}

    @Test
    fun checkPreferredContactMethodOptions() = runTest {}

    // Emergency Contact Area

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