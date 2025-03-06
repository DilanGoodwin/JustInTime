package com.dbad.justintime.f_login_register.presentation.login

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.TestTagCalendarView
import com.dbad.justintime.core.presentation.util.TestTagEmailField
import com.dbad.justintime.core.presentation.util.TestTagPasswordField
import com.dbad.justintime.f_login_register.data.UsersRepositoryTestingImplementation
import com.dbad.justintime.f_login_register.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import com.dbad.justintime.f_login_register.domain.use_case.GetEmergencyContactKey
import com.dbad.justintime.f_login_register.domain.use_case.GetEmployeeKey
import com.dbad.justintime.f_login_register.domain.use_case.GetUser
import com.dbad.justintime.f_login_register.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.f_login_register.domain.use_case.UpsertEmployee
import com.dbad.justintime.f_login_register.domain.use_case.UpsertUser
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.domain.use_case.ValidateDate
import com.dbad.justintime.f_login_register.domain.use_case.ValidateEmail
import com.dbad.justintime.f_login_register.domain.use_case.ValidatePassword
import com.dbad.justintime.f_login_register.domain.use_case.ValidatePhoneNumber
import com.dbad.justintime.f_login_register.presentation.LoginTestingNavController
import com.dbad.justintime.util.emailValidation
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTestingUI {

    private val validEmail: String = "testing@testing.com"
    private val validPassword: String = "MyP@ssw0rds"

    private lateinit var useCases: UserUseCases
    private val users: List<User> =
        listOf(
            User(uid = 0, email = validEmail, password = validPassword),
            User(uid = 1, email = "test.test@test.com", password = validPassword)
        )

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun reset() = runTest {
        val userRepo: UserRepository = UsersRepositoryTestingImplementation(users = users)
        useCases = UserUseCases(
            getUser = GetUser(repository = userRepo),
            upsertUser = UpsertUser(repository = userRepo),
            getEmployeeKey = GetEmployeeKey(repository = userRepo),
            upsertEmployee = UpsertEmployee(repository = userRepo),
            getEmergencyContactKey = GetEmergencyContactKey(repository = userRepo),
            upsertEmergencyContact = UpsertEmergencyContact(repository = userRepo),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validateDate = ValidateDate()
        )

        testRule.setContent {
            LoginTestingNavController(useCases = useCases)
        }
    }

    private fun checkValuesExist(screenValue: String) {
        testRule.onNodeWithText(text = screenValue)
            .assertExists(errorMessageOnFail = "Login Screen component '$screenValue' does not exist")
            .assertIsDisplayed()
    }

    @Test
    fun checkValuesDisplayed() = runTest {
        checkValuesExist(screenValue = testRule.activity.getString(R.string.email))
        checkValuesExist(screenValue = testRule.activity.getString(R.string.password))
        checkValuesExist(screenValue = testRule.activity.getString(R.string.login))
        checkValuesExist(screenValue = testRule.activity.getString(R.string.register))
    }

    @Test
    fun checkEmailFieldErrors() = runTest {
        emailValidation(
            testRule = testRule,
            emailField = testRule.onNodeWithTag(testTag = TestTagEmailField),
            expectedError = testRule.activity.getString(R.string.emailOrPasswordError),
            buttonToPress = testRule.activity.getString(R.string.login)
        )

        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
    }

    @Test
    fun checkPasswordFieldErrors() = runTest {
        var invalidPassword = ""
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = invalidPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.login)).performClick()
        testRule.onAllNodesWithText(text = testRule.activity.getString(R.string.emailOrPasswordError))
            .onFirst().assertIsDisplayed()

        invalidPassword = "password"
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = invalidPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.login)).performClick()
        testRule.onAllNodesWithText(text = testRule.activity.getString(R.string.emailOrPasswordError))
            .onFirst().assertIsDisplayed()
    }

    @Test
    fun checkValidLoginAttempt() = runTest { //TODO not implemented
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.login)).performClick()

        testRule.onNodeWithTag(testTag = TestTagCalendarView).assertIsDisplayed()
    }

    @Test
    fun checkInvalidLoginAttempt() = runTest {
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = "test@test.com")
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = "P4ssw0rd!")
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.login)).performClick()

        testRule.onAllNodesWithText(text = testRule.activity.getString(R.string.emailOrPasswordError))
            .onFirst().assertIsDisplayed()
    }

    @Test
    fun checkRegisterButton() = runTest {
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.login))
            .assertIsNotDisplayed()
    }
}