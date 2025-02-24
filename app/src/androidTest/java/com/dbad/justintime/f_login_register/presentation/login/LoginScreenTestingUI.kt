package com.dbad.justintime.f_login_register.presentation.login

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.TestTagCalendarView
import com.dbad.justintime.core.presentation.TestTagEmailField
import com.dbad.justintime.core.presentation.TestTagErrorNotifier
import com.dbad.justintime.core.presentation.TestTagPasswordField
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTestingUI {

    //TODO Fill in values for testing
    private val validEmail: String = ""
    private val validPassword: String = ""

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun reset() = runTest {
        testRule.setContent { LoginScreen() }
    }

    private fun checkValuesExist(screenValue: String) {
        testRule.onNodeWithText(text = screenValue)
            .assertExists(errorMessageOnFail = "Login Screen component '$screenValue' does not exist")
            .assertIsDisplayed()
    }

    @Test
    fun checkValuesDisplayed() = runTest {
        checkValuesExist(screenValue = testRule.activity.getString(R.string.email))
        checkValuesExist(screenValue = testRule.activity.getString(R.string.password))
        checkValuesExist(screenValue = testRule.activity.getString(R.string.login))
        checkValuesExist(screenValue = testRule.activity.getString(R.string.register))
    }

    @Test
    fun checkEmailFieldErrors() = runTest {
        var invalidEmail = "test"
        testRule.onNodeWithTag(testTag = TestTagEmailField).performTextInput(text = invalidEmail)
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier)
            .assertTextContains(value = testRule.activity.getString(R.string.invalidEmailError))

        invalidEmail = "test@test"
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = invalidEmail)
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier)
            .assertTextContains(value = testRule.activity.getString(R.string.invalidEmailError))

        invalidEmail = "test.test@test"
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = invalidEmail)
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier)
            .assertTextContains(value = testRule.activity.getString(R.string.invalidEmailError))

        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertIsNotDisplayed()

        invalidEmail = "test.test@test.com"
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = invalidEmail)
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertIsNotDisplayed()
    }

    @Test
    fun checkPasswordFieldErrors() = runTest {
        var invalidPassword = ""
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = invalidPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.login)).performClick()
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier)
            .assertTextContains(value = testRule.activity.getString(R.string.emailOrPasswordError))

        invalidPassword = "password"
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = invalidPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.login)).performClick()
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier)
            .assertTextContains(value = testRule.activity.getString(R.string.emailOrPasswordError))
    }

    @Test
    fun checkValidLoginAttempt() = runTest {
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.login)).performClick()

        testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertIsNotDisplayed()
        testRule.onNodeWithTag(testTag = TestTagCalendarView).assertIsDisplayed()
    }

    @Test
    fun checkInvalidLoginAttempt() = runTest {
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = "test@test.com")
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = "P4ssw0rd!")
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.login)).performClick()

        testRule.onNodeWithTag(testTag = TestTagErrorNotifier)
            .assertTextContains(value = testRule.activity.getString(R.string.emailOrPasswordError))
    }

    @Test
    fun checkRegisterButtonWithoutEmail() = runTest {
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagEmailField).assertIsDisplayed()
    }

    @Test
    fun checkRegisterButtonWithEmail() = runTest {
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagEmailField).assertTextContains(value = validEmail)
    }
}