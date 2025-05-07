package com.dbad.justintime.util

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.TestTagPasswordField
import com.dbad.justintime.core.presentation.util.TestTagPasswordMatchField

fun emailValidation(
    testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>,
    emailField: SemanticsNodeInteraction,
    expectedError: String,
    buttonToPress: String
) {
    fun inputEmail(emailText: String) {
        emailField.performTextReplacement(text = emailText)
        testRule.onNodeWithText(text = buttonToPress).performClick()
        testRule.onAllNodesWithText(text = expectedError)
            .onFirst().assertIsDisplayed()
    }

    inputEmail(emailText = "test")
    inputEmail(emailText = "test@test")
    inputEmail(emailText = "test.test@test")

    emailField.performTextReplacement(text = "test.test@test.com")
    testRule.onNodeWithText(text = buttonToPress).performClick()
}

fun phoneNumberValidation(
    testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>,
    phoneField: SemanticsNodeInteraction,
    buttonToPress: String
) {
    phoneField.performTextReplacement(text = "07")
    testRule.onNodeWithText(text = buttonToPress).performClick()
    testRule.onAllNodesWithText(text = testRule.activity.getString(R.string.invalidPhoneNumb))
        .onFirst().assertIsDisplayed()

    phoneField.performTextReplacement(text = "07665599200")
    testRule.onNodeWithText(text = buttonToPress).performClick()
}

fun contactMethodValidation(
    testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>,
    contactField: SemanticsNodeInteraction
) {
    contactField.performClick()
    testRule.onNodeWithText(text = testRule.activity.getString(R.string.email)).assertIsDisplayed()
    testRule.onNodeWithText(text = testRule.activity.getString(R.string.phone))
        .assertIsDisplayed().performClick()
    testRule.onNodeWithText(text = testRule.activity.getString(R.string.phone))
        .assertIsDisplayed()
}

fun passwordValidation(
    testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>,
    buttonToPress: String,
    buttonShown: Boolean = true
) {
    fun inputPassword(password: String, expectedError: String) {
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = password)
        testRule.onNodeWithTag(testTag = TestTagPasswordMatchField)
            .performTextReplacement(text = password)
        if (buttonShown) {
            testRule.onNodeWithText(text = buttonToPress)
                .performClick()
        }
        testRule.onNodeWithText(expectedError).assertIsDisplayed()
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
    testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>,
    buttonToPress: String,
    buttonShown: Boolean = true
) {
    fun inputMatchingPassword(password: String, errorExpected: Boolean = true) {
        testRule.onNodeWithTag(testTag = TestTagPasswordMatchField)
            .performTextReplacement(text = password)
        if (buttonShown) {
            testRule.onNodeWithText(text = buttonToPress)
                .performClick()
        }
        if (errorExpected) {
            testRule.onNodeWithText(text = testRule.activity.getString(R.string.passwordDoNotMatch))
                .assertIsDisplayed()
        }
    }

    val firstPassword = "MyP@ssw0rds"
    testRule.onNodeWithTag(testTag = TestTagPasswordField)
        .performTextReplacement(text = firstPassword)

    inputMatchingPassword(password = "password")
    inputMatchingPassword(password = "MyPassword")
    inputMatchingPassword(password = firstPassword, errorExpected = false)
}