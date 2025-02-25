package com.dbad.justintime.util

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.TestTagEmailField
import com.dbad.justintime.core.presentation.TestTagEmergencyContactExpandableField
import com.dbad.justintime.core.presentation.TestTagErrorNotifier
import com.dbad.justintime.core.presentation.TestTagPasswordField
import com.dbad.justintime.core.presentation.TestTagPasswordMatchField
import com.dbad.justintime.core.presentation.TestTagPhoneNumberField

fun emailValidation(
    testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
) {
    var invalidEmail = "test"
    testRule.onNodeWithTag(testTag = TestTagEmailField).performTextReplacement(text = invalidEmail)
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

    invalidEmail = "test.test@test.com"
    testRule.onNodeWithTag(testTag = TestTagEmailField)
        .performTextReplacement(text = invalidEmail)
    testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertIsNotDisplayed()
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

    val firstPassword: String = "MyP@ssw0rd"
    testRule.onNodeWithTag(testTag = TestTagPasswordField)
        .performTextReplacement(text = firstPassword)

    inputMatchingPassword(password = "password")
    inputMatchingPassword(password = "MyPassword")
    inputMatchingPassword(password = firstPassword, errorExpected = false)
}

fun fillInEmergencyContact(
    testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>,
    name: String = "",
    phone: String = "",
    email: String = ""
) {
    testRule.onNodeWithTag(testTag = TestTagEmailField)
        .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
        .performTextReplacement(text = name)
    testRule.onNodeWithTag(testTag = TestTagEmailField)
        .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
        .performTextReplacement(text = email)
    testRule.onNodeWithTag(testTag = TestTagPhoneNumberField)
        .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
        .performTextReplacement(text = phone)
}

fun phoneNumberValidation(
    testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>,
    buttonToPress: String
) {
    testRule.onNodeWithTag(testTag = TestTagPhoneNumberField)
        .performTextReplacement(text = "07")
    testRule.onNodeWithText(text = buttonToPress).performClick()
    testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertTextContains(
        substring = true,
        value = testRule.activity.getString(R.string.invalidPhoneNumb)
    )

    testRule.onNodeWithTag(testTag = TestTagPhoneNumberField)
        .performTextReplacement(text = "07665599200")
    testRule.onNodeWithText(text = buttonToPress).performClick()
    if (testRule.onNodeWithTag(testTag = TestTagErrorNotifier).isDisplayed()) {
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier)
            .assertTextContains(value = testRule.activity.getString(R.string.savingMessage))
    }
}