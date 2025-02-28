package com.dbad.justintime.f_login_register.presentation.register

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.TestTagEmailField
import com.dbad.justintime.core.presentation.TestTagErrorNotifier
import com.dbad.justintime.core.presentation.TestTagPasswordField
import com.dbad.justintime.core.presentation.TestTagPasswordMatchField
import com.dbad.justintime.util.emailValidation
import com.dbad.justintime.util.passwordMatchValidation
import com.dbad.justintime.util.passwordValidation
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterPrimaryScreenTestingUI {

    //TODO Fill in values for testing
    private val validEmail: String = ""
    private val validPassword: String = ""

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun reset() = runTest {
        testRule.setContent { RegisterScreen(RegisterViewModel()) }
    }

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
        emailValidation(
            testRule = testRule,
            emailField = testRule.onNodeWithTag(testTag = TestTagEmailField),
            buttonToPress = testRule.activity.getString(R.string.register)
        )

        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertIsNotDisplayed()
    }

    @Test
    fun checkPasswordFieldErrors() = runTest {
        passwordValidation(testRule = testRule)

        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithTag(testTag = TestTagPasswordMatchField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertIsNotDisplayed()
    }

    @Test
    fun checkPasswordMatchFieldErrors() = runTest {
        passwordMatchValidation(testRule = testRule)
    }

    @Test
    fun checkCancelButton() = runTest {
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.cancel)).performClick()
        testRule.onNodeWithTag(testTag = TestTagEmailField).assertTextContains(value = validEmail)
    }

    @Test
    fun checkRegisterButton() = runTest {
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithTag(testTag = TestTagPasswordMatchField)
            .performTextReplacement(text = validPassword)

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.next))
            .assertIsDisplayed()
    }

    @Test
    fun checkInvalidRegistrationAttempt() = runTest {
        // No details entered
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertTextContains(
            substring = true,
            value = testRule.activity.getString(R.string.enterEmail)
        )
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertTextContains(
            substring = true,
            value = testRule.activity.getString(R.string.enterPassword)
        )

        // No passwords
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertTextContains(
            substring = true,
            value = testRule.activity.getString(R.string.enterPassword)
        )

        // No matching password
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertTextContains(
            substring = true,
            value = testRule.activity.getString(R.string.passwordDoNotMatch)
        )

        // No email
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithTag(testTag = TestTagPasswordMatchField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertTextContains(
            substring = true,
            value = testRule.activity.getString(R.string.enterPassword)
        )
    }
}