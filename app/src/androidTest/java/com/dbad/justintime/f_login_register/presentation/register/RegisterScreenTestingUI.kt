package com.dbad.justintime.f_login_register.presentation.register

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextReplacement
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.TestTagEmailField
import com.dbad.justintime.core.presentation.TestTagErrorNotifier
import com.dbad.justintime.util.emailValidation
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterScreenTestingUI {

    //TODO Fill in values for testing
    private val validEmail: String = ""

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun reset() = runTest {
        testRule.setContent { RegisterScreen() }
    }

    // Primary Screen

    @Test
    fun checkRegistrationPrimaryScreenValuesDisplayed() = runTest {
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.email))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.password))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.rePassword))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.cancel))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .assertIsDisplayed()
    }

    @Test
    fun checkEmailFieldErrors() = runTest {
        emailValidation(testRule = testRule)

        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertIsNotDisplayed()
    }

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