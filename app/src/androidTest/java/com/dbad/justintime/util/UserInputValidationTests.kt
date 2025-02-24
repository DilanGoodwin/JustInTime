package com.dbad.justintime.util

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.TestTagEmailField
import com.dbad.justintime.core.presentation.TestTagErrorNotifier

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