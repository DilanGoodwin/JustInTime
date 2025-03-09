package com.dbad.justintime.f_login_register.presentation.register

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.dbad.justintime.R
import com.dbad.justintime.core.domain.model.User
import com.dbad.justintime.core.presentation.util.TestTagEmailField
import com.dbad.justintime.core.presentation.util.TestTagErrorNotifier
import com.dbad.justintime.core.presentation.util.TestTagPasswordField
import com.dbad.justintime.core.presentation.util.TestTagPasswordMatchField
import com.dbad.justintime.f_login_register.data.UsersRepositoryTestingImplementation
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
import com.dbad.justintime.util.passwordMatchValidation
import com.dbad.justintime.util.passwordValidation
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterPrimaryScreenTestingUI {

    private val validEmail: String = "test@test.com"
    private val validPassword: String = "MyP@ssw0rds"

    private lateinit var useCases: UserUseCases
    private val users: List<User> = listOf(
        User(
            uid = User.generateUid(email = "testing@testing.com"),
            email = "testing@testing.com",
            password = User.hashPassword(validPassword)
        ),
        User(
            uid = User.generateUid(email = "test.test@test.com"),
            email = "test.test@test.com",
            password = User.hashPassword(validPassword)
        ),
        User(uid = User.generateUid(email = validEmail), email = validEmail)
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

        testRule.setContent { LoginTestingNavController(useCases = useCases) }
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
    }

    @Test
    fun checkRegistrationPrimaryScreenValuesDisplayed() = runTest {
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.email))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.password))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.rePassword))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.cancel))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .assertIsDisplayed()
    }

    @Test
    fun checkEmailFieldErrors() = runTest {
        emailValidation(
            testRule = testRule,
            emailField = testRule.onNodeWithTag(testTag = TestTagEmailField),
            expectedError = testRule.activity.getString(R.string.invalidEmailError),
            buttonToPress = testRule.activity.getString(R.string.register)
        )

        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertIsNotDisplayed()
    }

    @Test
    fun checkPasswordFieldErrors() = runTest {
        passwordValidation(testRule = testRule)

        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithTag(testTag = TestTagPasswordMatchField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagErrorNotifier).assertIsNotDisplayed()
    }

    @Test
    fun checkPasswordMatchFieldErrors() = runTest {
        passwordMatchValidation(testRule = testRule)
    }

    @Test
    fun checkCancelButton() = runTest {
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.cancel)).performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.login))
            .assertIsDisplayed()
    }

    @Test
    fun checkRegisterButton() = runTest {
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithTag(testTag = TestTagPasswordMatchField)
            .performTextReplacement(text = validPassword)

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.emergencyContact))
            .assertIsDisplayed()
    }

    @Test
    fun checkInvalidRegistrationAttempt_NoDetails() = runTest {
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.invalidEmailError))
            .assertIsDisplayed()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.enterPassword))
            .assertIsDisplayed()
    }

    @Test
    fun checkInvalidRegistrationAttempt_NoEmail() = runTest {
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithTag(testTag = TestTagPasswordMatchField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.invalidEmailError))
            .assertIsDisplayed()

    }

    @Test
    fun checkInvalidRegistrationAttempt_NoPassword() = runTest {
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.enterPassword))
            .assertIsDisplayed()
    }

    @Test
    fun checkInvalidRegistrationAttempt_NoMatchingPassword() = runTest {
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = validEmail)
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.passwordDoNotMatch))
            .assertIsDisplayed()
    }

    @Test
    fun checkInvalidRegistrationAttempt_EmailAlreadyUsed() = runTest {
        testRule.onNodeWithTag(testTag = TestTagEmailField)
            .performTextReplacement(text = "testing@testing.com")
        testRule.onNodeWithTag(testTag = TestTagPasswordField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithTag(testTag = TestTagPasswordMatchField)
            .performTextReplacement(text = validPassword)
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.register))
            .performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.invalidEmailError))
            .assertIsDisplayed()
    }
}