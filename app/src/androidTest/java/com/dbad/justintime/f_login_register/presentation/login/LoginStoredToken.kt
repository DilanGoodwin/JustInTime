package com.dbad.justintime.f_login_register.presentation.login

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.dbad.justintime.R
import com.dbad.justintime.core.domain.model.User
import com.dbad.justintime.core.presentation.util.TestTagEmailField
import com.dbad.justintime.core.presentation.util.TestTagPasswordField
import com.dbad.justintime.f_login_register.data.UserPreferencesTestingImplementation
import com.dbad.justintime.f_login_register.data.generateUseCase
import com.dbad.justintime.f_login_register.domain.user_case.UserUseCases
import com.dbad.justintime.f_login_register.presentation.LoginTestingNavController
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginStoredToken {
    private val validEmail: String = "testing@testing.com"
    private val validPassword: String = "MyP@ssw0rds"

    private lateinit var useCases: UserUseCases

    private val users: List<User> = listOf(
        User(
            uid = User.generateUid(email = validEmail),
            email = validEmail,
            password = User.hashPassword(validPassword)
        )
    )

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun reset() = runTest {
        useCases = generateUseCase(users = users)
    }

    @Test
    fun checkAutoLoginFromStoredToken() = runTest {
        testRule.setContent {
            LoginTestingNavController(
                useCases = useCases,
                userPreferencesStore = UserPreferencesTestingImplementation(
                    passedState = User.generateUid(email = validEmail)
                )
            )
        }

        testRule.onNodeWithText(text = testRule.activity.getString(R.string.profile))
    }

    @Test
    fun checkNoAutoLogin() = runTest {
        testRule.setContent {
            LoginTestingNavController(
                useCases = useCases,
                userPreferencesStore = UserPreferencesTestingImplementation()
            )
        }

        testRule.onNodeWithTag(testTag = TestTagEmailField).assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagPasswordField).assertIsDisplayed()
    }
}