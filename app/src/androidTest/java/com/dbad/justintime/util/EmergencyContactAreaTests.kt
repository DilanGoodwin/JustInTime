package com.dbad.justintime.util

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.TestTagEmailField
import com.dbad.justintime.core.presentation.util.TestTagEmergencyContactExpandableField
import com.dbad.justintime.core.presentation.util.TestTagEmergencyContactRelation
import com.dbad.justintime.core.presentation.util.TestTagPhoneNumberField
import com.dbad.justintime.core.presentation.util.TestTagPreferredContactMethodField
import com.dbad.justintime.f_login_register.domain.model.util.Relation

class EmergencyContactAreaTests(
    private val testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
) {

    private val name: String = "Daniel"
    private val validPhoneNumb: String = "07665599200"
    private val validEmail: String = "daniel@justintime.com"

    fun checkExpandedEmergencyContactValuesDisplayed() {
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.cancel))
            .performScrollTo()
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).onChildren()
            .filterToOne(matcher = hasText(text = testRule.activity.getString(R.string.name)))
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).onChildren()
            .filterToOne(matcher = hasText(text = testRule.activity.getString(R.string.preferredName)))
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).onChildren()
            .filterToOne(matcher = hasText(text = testRule.activity.getString(R.string.phoneNumb)))
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).onChildren()
            .filterToOne(matcher = hasText(text = testRule.activity.getString(R.string.email)))
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).onChildren()
            .filterToOne(matcher = hasText(text = testRule.activity.getString(R.string.prefContactMethod)))
            .assertIsDisplayed()
    }

    fun checkEmergencyContactPhoneErrors() {
        fillInEmergencyContact(testRule = testRule, name = name)
        phoneNumberValidation(
            testRule = testRule,
            phoneField = testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField)
                .onChildren().filterToOne(matcher = hasTestTag(testTag = TestTagPhoneNumberField)),
            buttonToPress = testRule.activity.getString(R.string.register)
        )
    }

    fun checkEmergencyContactEmailErrors() {
        fillInEmergencyContact(testRule = testRule, name = name, phone = validPhoneNumb)

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.cancel))
            .performScrollTo()

        emailValidation(
            testRule = testRule,
            emailField = testRule.onNodeWithTag(testTag = TestTagEmailField)
                .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField))),
            expectedError = testRule.activity.getString(R.string.invalidEmailError),
            buttonToPress = testRule.activity.getString(R.string.register)
        )
    }

    fun checkEmergencyContactPreferredContactMethodOptions() {
        fillInEmergencyContact(
            testRule = testRule,
            name = name,
            phone = validPhoneNumb,
            email = validEmail
        )

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.cancel))
            .performScrollTo()

        contactMethodValidation(
            testRule = testRule,
            contactField = testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField)
                .onChildren()
                .filterToOne(matcher = hasText(text = testRule.activity.getString(R.string.prefContactMethod)))
                .onChildren()
                .filterToOne(matcher = hasTestTag(testTag = TestTagPreferredContactMethodField))
        )
    }

    fun checkRelationOptions() {
        fillInEmergencyContact(
            testRule = testRule,
            name = name,
            phone = validPhoneNumb,
            email = validEmail
        )

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.cancel))
            .performScrollTo()

        // Check all values displayed
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactRelation)
            .performClick()

        for (rel in Relation.entries) {
            if (rel != Relation.NONE) {
                testRule.onNodeWithText(text = testRule.activity.getString(rel.stringVal))
                    .assertIsDisplayed()
            }
        }

        // Select a relation and check it is displayed
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.mother)).performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.mother))
            .assertIsDisplayed()
    }

    companion object {
        fun fillInEmergencyContact(
            testRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>,
            name: String = "",
            phone: String = "",
            email: String = ""
        ) {
            testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).performClick()
            testRule.onAllNodesWithText(text = testRule.activity.getString(R.string.name))
                .filterToOne(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
                .performTextReplacement(text = name)
            testRule.onAllNodesWithTag(testTag = TestTagPhoneNumberField)
                .filterToOne(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
                .performTextReplacement(text = phone)
            testRule.onAllNodesWithTag(testTag = TestTagEmailField)
                .filterToOne(matcher = hasParent(matcher = hasTestTag(testTag = TestTagEmergencyContactExpandableField)))
                .performTextReplacement(text = email)
        }
    }
}

