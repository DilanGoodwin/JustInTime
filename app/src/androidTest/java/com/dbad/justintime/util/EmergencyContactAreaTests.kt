package com.dbad.justintime.util

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.TestTagEmailField
import com.dbad.justintime.core.presentation.util.TestTagEmergencyContactExpandableField
import com.dbad.justintime.core.presentation.util.TestTagPhoneNumberField
import com.dbad.justintime.core.presentation.util.TestTagPreferredContactMethodField

class EmergencyContactAreaTests(
    private val testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
) {

    private val name: String = "Daniel"
    private val validPhoneNumb: String = "07665599200"
    private val validEmail: String = "daniel@justintime.com"

    fun checkExpandedEmergencyContactValuesDisplayed() {
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.name))
            .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.preferredName))
            .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.phoneNumb))
            .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.email))
            .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagPreferredContactMethodField)
            .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
            .assertIsDisplayed()
    }

    fun checkEmergencyContactPhoneErrors() {
        fillInEmergencyContact(testRule = testRule, name = name)
        phoneNumberValidation(
            testRule = testRule,
            phoneField = testRule.onNodeWithTag(testTag = TestTagPhoneNumberField)
                .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField))),
            buttonToPress = testRule.activity.getString(R.string.next)
        )
    }

    fun checkEmergencyContactEmailErrors() {
        fillInEmergencyContact(testRule = testRule, name = name, phone = validPhoneNumb)
        emailValidation(
            testRule = testRule,
            emailField = testRule.onNodeWithTag(testTag = TestTagEmailField)
                .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField))),
            expectedError = testRule.activity.getString(R.string.invalidEmailError),
            buttonToPress = testRule.activity.getString(R.string.next)
        )
    }

    fun checkEmergencyContactPreferredContactMethodOptions() {
        fillInEmergencyContact(
            testRule = testRule,
            name = name,
            phone = validPhoneNumb,
            email = validEmail
        )
        contactMethodValidation(
            testRule = testRule,
            parentNode = hasParent(matcher = hasTestTag(testTag = TestTagPreferredContactMethodField))
        )
    }

    companion object {
        fun fillInEmergencyContact(
            testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>,
            name: String = "",
            phone: String = "",
            email: String = ""
        ) {
            testRule.onNodeWithText(text = testRule.activity.getString(R.string.name))
                .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
                .performTextReplacement(text = name)
            testRule.onNodeWithTag(testTag = TestTagPhoneNumberField)
                .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
                .performTextReplacement(text = phone)
            testRule.onNodeWithTag(testTag = TestTagEmailField)
                .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
                .performTextReplacement(text = email)
        }
    }
}

