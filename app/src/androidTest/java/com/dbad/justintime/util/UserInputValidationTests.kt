package com.dbad.justintime.util

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.TestTagEmailField
import com.dbad.justintime.core.presentation.TestTagErrorNotifier
import com.dbad.justintime.core.presentation.TestTagPasswordField
import com.dbad.justintime.core.presentation.TestTagPasswordMatchField
import com.dbad.justintime.core.presentation.TestTagPreferredContactMethodField

fun emailValidation(
    testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>,
    emailField: SemanticsNodeInteraction,
    buttonToPress: String
) {
    fun inputEmail(emailText: String) {
        emailField.performTextReplacement(text = emailText)
        testRule.onNodeWithText(text = buttonToPress).performClick()
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier)
            .assertTextContains(value = testRule.activity.getString(R.string.invalidEmailError))
    }

    inputEmail(emailText = "test")
    inputEmail(emailText = "test@test")
    inputEmail(emailText = "test.test@test")

    testRule.onNodeWithTag(testTag = TestTagEmailField)
        .performTextReplacement(text = "test.test@test.com")
    testRule.onNodeWithText(text = buttonToPress).performClick()
    if (testRule.onNodeWithTag(testTag = TestTagErrorNotifier).isDisplayed()) {
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier)
            .assertTextContains(value = testRule.activity.getString(R.string.savingMessage))
    }
}

fun phoneNumberValidation(
    testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>,
    phoneField: SemanticsNodeInteraction,
    buttonToPress: String
) {
    phoneField.performTextReplacement(text = "07")
    testRule.onNodeWithText(text = buttonToPress).performClick()
    testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertTextContains(
        substring = true,
        value = testRule.activity.getString(R.string.invalidPhoneNumb)
    )

    phoneField.performTextReplacement(text = "07665599200")
    testRule.onNodeWithText(text = buttonToPress).performClick()
    if (testRule.onNodeWithTag(testTag = TestTagErrorNotifier).isDisplayed()) {
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier)
            .assertTextContains(value = testRule.activity.getString(R.string.savingMessage))
    }
}

fun contactMethodValidation(
    testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>,
    parentNode: SemanticsMatcher
) {
    testRule.onNodeWithTag(testTag = TestTagPreferredContactMethodField).performClick()
    testRule.onNodeWithText(text = testRule.activity.getString(R.string.email)).assert(parentNode)
        .assertIsDisplayed()
    testRule.onNodeWithText(text = testRule.activity.getString(R.string.phoneNumb))
        .assert(parentNode).assertIsDisplayed().performClick()
    testRule.onNodeWithText(text = testRule.activity.getString(R.string.phoneNumb))
        .assert(parentNode).isDisplayed()
}

fun passwordValidation(
    testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
) {
    fun inputPassword(password: String, expectedError: String) {
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = password)
        testRule.onNodeWithTag(testTag = TestTagPasswordMatchField)
            .performTextReplacement(text = password)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier)
            .assertTextContains(substring = true, value = expectedError)
    }

    inputPassword(password = "p@ssw0rdss", testRule.activity.getString(R.string.passwordNoCapitals))
    inputPassword(
        password = "P@SSW0RDSS",
        testRule.activity.getString(R.string.passwordNoLowercase)
    )
    inputPassword(
        password = "P@sswordss",
        expectedError = testRule.activity.getString(R.string.passwordNoDigits)
    )
    inputPassword(
        password = "P4ssw0rdss",
        expectedError = testRule.activity.getString(R.string.passwordNoSymbols)
    )
    inputPassword(
        password = "P@ssw0rds",
        expectedError = testRule.activity.getString(R.string.passwordLength)
    )
}

fun passwordMatchValidation(
    testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
) {
    fun inputMatchingPassword(password: String, errorExpected: Boolean = true) {
        testRule.onNodeWithTag(testTag = TestTagPasswordMatchField)
            .performTextReplacement(text = password)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()

        if (errorExpected) {
            testRule.onNodeWithTag(testTag = TestTagErrorNotifier)
                .assertTextContains(
                    substring = true,
                    value = testRule.activity.getString(R.string.passwordDoNotMatch)
                )
        }
    }

    val firstPassword = "MyP@ssw0rd"
    testRule.onNodeWithTag(testTag = TestTagPasswordField)
        .performTextReplacement(text = firstPassword)

    inputMatchingPassword(password = "password")
    inputMatchingPassword(password = "MyPassword")
    inputMatchingPassword(password = firstPassword, errorExpected = false)
}