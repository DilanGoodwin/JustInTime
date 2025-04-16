package com.dbad.justintime.f_login_register.presentation.register

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
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
import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.model.User
import com.dbad.justintime.f_login_register.data.generateUseCase
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.presentation.LoginTestingNavController
import com.dbad.justintime.util.EmergencyContactAreaTests
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class UserDetailsValidationTests {
    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    private val validUser: User = User(
        uid = User.generateUid(email = "daniel@justintime.com"),
        email = "daniel@justintime.com"
    )
    private val name: String = "Daniel"
    private val validPassword: String = "MyP@ssw0rds"
    private val validPhoneNumb: String = "07665599200"

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
    private var useCases: UserUseCases = generateUseCase(
        users = users,
        employees = listOf(
            Employee(uid = "TmpEmployee", emergencyContact = "TmpEmergencyContact")
        ),
        emergencyContact = listOf(EmergencyContact(uid = "TmpEmergencyContact"))
    )

    private fun fillInDetails(
        name: String = "",
        phone: String = "",
    ) {
        testRule.onNodeWithTag(testTag = TestTagNameField).performTextReplacement(text = name)
        testRule.onNodeWithTag(testTag = TestTagPhoneNumberField)
            .performTextReplacement(text = phone)
    }

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

    @Test
    fun invalidRegistrationAttempt_NoName() = runTest {
        testRule.setContent {
            LoginTestingNavController(
                useCases = useCases,
                authUser = AuthTestingRepo(user = validUser),
                dateOfBirth = "20/04/2008"
            )
        }
        navigateToUserDetails()
        fillInDetails(phone = validPhoneNumb)
        EmergencyContactAreaTests.fillInEmergencyContact(
            testRule = testRule,
            name = name,
            phone = validPhoneNumb,
            email = validUser.email
        )

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performScrollTo().performClick()
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).performScrollTo()
            .performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.noNameProvided))
            .performScrollTo().assertIsDisplayed()
    }

    @Test
    fun invalidRegistrationAttempt_NoPhone() = runTest {
        testRule.setContent {
            LoginTestingNavController(
                useCases = useCases,
                authUser = AuthTestingRepo(user = validUser),
                dateOfBirth = "20/04/2008"
            )
        }
        navigateToUserDetails()
        fillInDetails(name = name)
        EmergencyContactAreaTests.fillInEmergencyContact(
            testRule = testRule,
            name = name,
            phone = validPhoneNumb,
            email = validUser.email
        )

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performScrollTo().performClick()
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).performScrollTo()
            .performClick()
        testRule.onAllNodesWithTag(testTag = TestTagPhoneNumberField).onFirst().performScrollTo()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.invalidPhoneNumb))
            .assertIsDisplayed()
    }

    @Test
    fun invalidRegistrationAttempt_NoDateOfBirth() = runTest {
        testRule.setContent {
            LoginTestingNavController(
                useCases = useCases,
                authUser = AuthTestingRepo(user = validUser)
            )
        }
        navigateToUserDetails()
        fillInDetails(name = name, phone = validPhoneNumb)
        EmergencyContactAreaTests.fillInEmergencyContact(
            testRule = testRule,
            name = name,
            phone = validPhoneNumb,
            email = validUser.email
        )

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performScrollTo().performClick()
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).performScrollTo()
            .performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.dobError))
            .performScrollTo().assertIsDisplayed()
    }

    @Test
    fun invalidRegistrationAttempt_NoPrefContact() = runTest {
        testRule.setContent {
            LoginTestingNavController(
                useCases = useCases,
                authUser = AuthTestingRepo(user = validUser),
                dateOfBirth = "20/04/2008"
            )
        }
        navigateToUserDetails()
        fillInDetails(name = name, phone = validPhoneNumb)

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performScrollTo().performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.noNameProvided))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.invalidPhoneNumb))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.invalidEmailError))
            .assertIsDisplayed()
    }

    @Test
    fun invalidRegistrationAttempt_PrefContact_NoName() = runTest {
        testRule.setContent {
            LoginTestingNavController(
                useCases = useCases,
                authUser = AuthTestingRepo(user = validUser),
                dateOfBirth = "20/04/2008"
            )
        }
        navigateToUserDetails()
        fillInDetails(name = name, phone = validPhoneNumb)
        EmergencyContactAreaTests.fillInEmergencyContact(
            testRule = testRule,
            phone = validPhoneNumb,
            email = validUser.email
        )

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performScrollTo().performClick()
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).performScrollTo()
            .performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.noNameProvided))
            .performScrollTo().assertIsDisplayed()
    }

    @Test
    fun invalidRegistrationAttempt_PrefContact_NoEmail() = runTest {
        testRule.setContent {
            LoginTestingNavController(
                useCases = useCases,
                authUser = AuthTestingRepo(user = validUser),
                dateOfBirth = "20/04/2008"
            )
        }
        navigateToUserDetails()
        fillInDetails(name = name, phone = validPhoneNumb)
        EmergencyContactAreaTests.fillInEmergencyContact(
            testRule = testRule,
            name = name,
            phone = validPhoneNumb
        )

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performScrollTo().performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.invalidEmailError))
            .performScrollTo().assertIsDisplayed()
    }

    @Test
    fun invalidRegistrationAttempt_PrefContact_NoPhone() = runTest {
        testRule.setContent {
            LoginTestingNavController(
                useCases = useCases,
                authUser = AuthTestingRepo(user = validUser),
                dateOfBirth = "20/04/2008"
            )
        }
        navigateToUserDetails()
        fillInDetails(name = name, phone = validPhoneNumb)
        EmergencyContactAreaTests.fillInEmergencyContact(
            testRule = testRule,
            name = name,
            email = validUser.email
        )

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performScrollTo().performClick()
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).performScrollTo()
            .performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.invalidPhoneNumb))
            .performScrollTo().assertIsDisplayed()
    }

    @Test
    fun validRegistrationAttempt() = runTest {
        testRule.setContent {
            LoginTestingNavController(
                useCases = useCases,
                authUser = AuthTestingRepo(user = validUser),
                dateOfBirth = "20/04/2008"
            )
        }
        navigateToUserDetails()
        fillInDetails(name = name, phone = validPhoneNumb)
        EmergencyContactAreaTests.fillInEmergencyContact(
            testRule = testRule,
            name = name,
            phone = validPhoneNumb,
            email = validUser.email
        )

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performScrollTo().performClick()
        testRule.onAllNodesWithText(text = testRule.activity.getString(R.string.profile)).onFirst()
            .assertIsDisplayed()
    }
}