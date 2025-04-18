package com.dbad.justintime.f_login_register.presentation.register

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import com.dbad.justintime.R
import com.dbad.justintime.core.AuthTestingRepo
import com.dbad.justintime.core.presentation.util.TestTagEmailField
import com.dbad.justintime.core.presentation.util.TestTagEmergencyContactExpandableField
import com.dbad.justintime.core.presentation.util.TestTagNameField
import com.dbad.justintime.core.presentation.util.TestTagPasswordField
import com.dbad.justintime.core.presentation.util.TestTagPasswordMatchField
import com.dbad.justintime.core.presentation.util.TestTagPhoneNumberField
import com.dbad.justintime.core.presentation.util.TestTagPreferredContactMethodField
import com.dbad.justintime.f_local_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_login_register.data.generateUseCase
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.presentation.LoginTestingNavController
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

    private val validUser: User = User(
        uid = User.generateUid(email = "daniel@justintime.com"),
        email = "daniel@justintime.com"
    )
    private val name: String = "Daniel"
    private val validPassword: String = "MyP@ssw0rds"
    private val validPhoneNumb: String = "07665599200"
    private val emergencyContactTests: EmergencyContactAreaTests =
        EmergencyContactAreaTests(testRule = testRule)

    private lateinit var useCases: UserUseCases
    private val users: List<User> = listOf(
        User(
            uid = User.generateUid(email = "testing@testing.com"),
            email = "testing@testing.com"
        ),
        User(
            uid = User.generateUid(email = "test.test@test.com"),
            email = "test.test@test.com"
        ),
        validUser
    )

    private fun navigateToUserDetails() {
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validUser.email)
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithTag(testTag = TestTagPasswordMatchField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
    }

    @Before
    fun reset() = runTest {
        useCases = generateUseCase(
            users = users,
            employees = listOf(
                Employee(uid = "TmpEmployee", emergencyContact = "TmpEmergencyContact")
            ),
            emergencyContact = listOf(EmergencyContact(uid = "TmpEmergencyContact"))
        )
        testRule.setContent {
            LoginTestingNavController(
                useCases = useCases,
                authUser = AuthTestingRepo(user = validUser)
            )
        }
        navigateToUserDetails()
    }

    @Test
    fun checkRegistrationSecondaryScreenValuesDisplayed() = runTest {

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.name))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.preferredName))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.dateOfBirth))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.phoneNumb))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.prefContactMethod))
            .assertIsDisplayed()

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.cancel))
            .performScrollTo()

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.emergencyContact))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.cancel))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).assertIsDisplayed()
    }

    @Test
    fun checkPhoneNumberErrors() = runTest {
        fillInEmergencyContact(
            testRule = testRule,
            name = name,
            phone = validPhoneNumb,
            email = validUser.email
        )

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.cancel))
            .performScrollTo()

        phoneNumberValidation(
            testRule = testRule,
            phoneField = testRule.onAllNodesWithTag(testTag = TestTagPhoneNumberField).onFirst(),
            buttonToPress = testRule.activity.getString(R.string.register)
        )
    }

    @Test
    fun checkPreferredContactMethodOptions() = runTest {
        testRule.onNodeWithTag(testTag = TestTagNameField).performTextReplacement(text = name)
        testRule.onNodeWithTag(testTag = TestTagPhoneNumberField)
            .performTextReplacement(text = validPhoneNumb)
        contactMethodValidation(
            testRule = testRule,
            contactField = testRule.onNodeWithTag(testTag = TestTagPreferredContactMethodField)
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

    @Test
    fun emergencyContactRelationOptions() = runTest {
        emergencyContactTests.checkRelationOptions()
    }
}