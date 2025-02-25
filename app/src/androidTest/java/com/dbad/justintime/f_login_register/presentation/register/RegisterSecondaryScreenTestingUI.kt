package com.dbad.justintime.f_login_register.presentation.register

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.TestTagEmergencyContactExpandableField
import com.dbad.justintime.core.presentation.TestTagNameField
import com.dbad.justintime.core.presentation.TestTagPreferredContactMethodField
import com.dbad.justintime.util.fillInEmergencyContact
import com.dbad.justintime.util.phoneNumberValidation
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterSecondaryScreenTestingUI {

    private val name: String = "Daniel"
    private val validEmail: String = "daniel@justintime.com"
    private val validPhoneNumb: String = "07665599200"

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun reset() = runTest {
        testRule.setContent {}
    }

    @Test
    fun checkRegistrationSecondaryScreenValuesDisplayed() = runTest {
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.name))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.preferredName))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.phoneNumb))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.cancel))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.next))
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagPreferredContactMethodField).assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).assertIsDisplayed()
    }

    @Test
    fun checkPhoneNumberErrors() = runTest {
        testRule.onNodeWithTag(testTag = TestTagNameField).performTextReplacement(text = name)
        fillInEmergencyContact(
            testRule = testRule,
            name = name,
            phone = validPhoneNumb,
            email = validEmail
        )
        phoneNumberValidation(
            testRule = testRule,
            buttonToPress = testRule.activity.getString(R.string.next)
        )
    }

    @Test
    fun checkPreferredContactMethodOptions() = runTest {
        testRule.onNodeWithTag(testTag = TestTagPreferredContactMethodField).performClick()
        testRule.onNodeWithTag(testTag = TestTagPreferredContactMethodField)
            .assert(matcher = hasAnyChild(matcher = hasText(text = testRule.activity.getString(R.string.email))))
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagPreferredContactMethodField)
            .assert(matcher = hasAnyChild(matcher = hasText(text = testRule.activity.getString(R.string.phoneNumb))))
            .assertIsDisplayed().performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.phoneNumb))
            .assert(matcher = hasParent(matcher = hasTestTag(testTag = TestTagPreferredContactMethodField)))
            .assertIsDisplayed()
    }
}