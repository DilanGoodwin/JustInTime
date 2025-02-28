package com.dbad.justintime.f_login_register.presentation.register

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextReplacement
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.TestTagEmergencyContactExpandableField
import com.dbad.justintime.core.presentation.util.TestTagNameField
import com.dbad.justintime.core.presentation.util.TestTagPhoneNumberField
import com.dbad.justintime.core.presentation.util.TestTagPreferredContactMethodField
import com.dbad.justintime.util.EmergencyContactAreaTests
import com.dbad.justintime.util.EmergencyContactAreaTests.Companion.fillInEmergencyContact
import com.dbad.justintime.util.contactMethodValidation
import com.dbad.justintime.util.phoneNumberValidation
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterSecondaryScreenTestingUI {

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    private val name: String = "Daniel"
    private val validEmail: String = "daniel@justintime.com"
    private val validPhoneNumb: String = "07665599200"
    private val emergencyContactTests: EmergencyContactAreaTests =
        EmergencyContactAreaTests(testRule = testRule)

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
            phoneField = testRule.onNodeWithTag(testTag = TestTagPhoneNumberField),
            buttonToPress = testRule.activity.getString(R.string.next)
        )
    }

    @Test
    fun checkPreferredContactMethodOptions() = runTest {
        testRule.onNodeWithTag(testTag = TestTagNameField).performTextReplacement(text = name)
        testRule.onNodeWithTag(testTag = TestTagPhoneNumberField)
            .performTextReplacement(text = validPhoneNumb)
        contactMethodValidation(
            testRule = testRule,
            parentNode = hasParent(matcher = hasTestTag(testTag = TestTagPreferredContactMethodField))
        )
    }

    @Test
    fun emergencyContactAreaDisplayedValues() = runTest {
        emergencyContactTests.checkExpandedEmergencyContactValuesDisplayed()
    }

    @Test
    fun emergencyContactAreaPhoneErrors() = runTest {
        emergencyContactTests.checkEmergencyContactPhoneErrors()
    }

    @Test
    fun emergencyContactAreaEmailErrors() = runTest {
        emergencyContactTests.checkEmergencyContactEmailErrors()
    }

    @Test
    fun emergencyContactAreaContactOptions() = runTest {
        emergencyContactTests.checkEmergencyContactPreferredContactMethodOptions()
    }
}