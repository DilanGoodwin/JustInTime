package com.dbad.justintime.f_profile.presentation.profile

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.dbad.justintime.R
import com.dbad.justintime.core.domain.use_case.ValidateDate
import com.dbad.justintime.core.domain.use_case.ValidateEmail
import com.dbad.justintime.core.domain.use_case.ValidatePassword
import com.dbad.justintime.core.domain.use_case.ValidatePhoneNumber
import com.dbad.justintime.core.presentation.util.TestTagOldPasswordField
import com.dbad.justintime.core.presentation.util.TestTagPasswordChangeExpandableField
import com.dbad.justintime.core.presentation.util.TestTagPasswordField
import com.dbad.justintime.core.presentation.util.TestTagPasswordMatchField
import com.dbad.justintime.core.presentation.util.TestTagProfileSaveButton
import com.dbad.justintime.f_profile.data.ProfileRepositoryTestingImplementation
import com.dbad.justintime.f_profile.data.ProfileRepositoryTestingImplementation.Companion.generateProfileTestRepo
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository
import com.dbad.justintime.f_profile.domain.use_case.GetEmergencyContact
import com.dbad.justintime.f_profile.domain.use_case.GetEmployee
import com.dbad.justintime.f_profile.domain.use_case.GetUser
import com.dbad.justintime.f_profile.domain.use_case.ProfileUseCases
import com.dbad.justintime.f_profile.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.f_profile.domain.use_case.UpsertEmployee
import com.dbad.justintime.f_profile.domain.use_case.UpsertUser
import com.dbad.justintime.f_profile.presentation.ProfileTestingNavController
import com.dbad.justintime.util.UserPreferencesTestingImplementation
import com.dbad.justintime.util.passwordMatchValidation
import com.dbad.justintime.util.passwordValidation
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileScreenPasswordTesting {

    private val oldPassword = "C@55@ndr@P4ssword"
    private val newPassword = "C@55@ndr@P4ssw0rd!"
    lateinit var useCases: ProfileUseCases

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    suspend fun waitOutLoadDelay() {
        delay(timeMillis = 5000L)
    }

    @Before
    fun reset() = runTest {
        val profileRepo: ProfileRepository = generateProfileTestRepo()

        useCases = ProfileUseCases(
            getUser = GetUser(repository = profileRepo),
            upsertUser = UpsertUser(repository = profileRepo),
            getEmployee = GetEmployee(repository = profileRepo),
            upsertEmployee = UpsertEmployee(repository = profileRepo),
            getEmergencyContact = GetEmergencyContact(repository = profileRepo),
            upsertEmergencyContact = UpsertEmergencyContact(repository = profileRepo),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validateDate = ValidateDate()
        )

        testRule.setContent {
            ProfileTestingNavController(
                useCases = useCases,
                userPreferencesStore = UserPreferencesTestingImplementation(
                    passedState = ProfileRepositoryTestingImplementation.userUid
                )
            )
        }

        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).performClick()
    }

    @Test
    fun checkFieldsDisplayed() = runTest {
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagOldPasswordField))
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordField))
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordMatchField))
            .assertIsDisplayed()
    }

    @Test
    fun checkOldPasswordError() {
        runBlocking { waitOutLoadDelay() }

        // Check save is not shown without old password filled
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagOldPasswordField))
            .assertIsDisplayed().performTextReplacement(text = "")
        testRule.onNodeWithTag(testTag = TestTagProfileSaveButton).assertIsNotDisplayed()

        // Check save is not show without new passwords filled
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagOldPasswordField))
            .assertIsDisplayed().performTextReplacement(text = oldPassword)
        testRule.onNodeWithTag(testTag = TestTagProfileSaveButton).assertIsNotDisplayed()

        // Check old password validation
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordField))
            .assertIsDisplayed().performTextReplacement(text = newPassword)
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordMatchField))
            .assertIsDisplayed().performTextReplacement(text = newPassword)
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagOldPasswordField))
            .assertIsDisplayed().performTextReplacement(text = "T3st!ngP4ssw0rds")
        testRule.onNodeWithTag(testTag = TestTagProfileSaveButton).assertIsDisplayed()
            .performClick()
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.passwordDoNotMatch))
            .assertIsDisplayed()

        // Update old Password
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagOldPasswordField))
            .assertIsDisplayed().performTextReplacement(text = oldPassword)
        testRule.onNodeWithTag(testTag = TestTagProfileSaveButton).assertIsDisplayed()
            .performClick()

        // Check fields have bee updated
        testRule.onNodeWithText(text = testRule.activity.getString(R.string.passwordDoNotMatch))
            .assertIsNotDisplayed()
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagOldPasswordField))
            .assertIsDisplayed().performTextReplacement(text = "")
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordField))
            .assertIsDisplayed().performTextReplacement(text = "")
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordMatchField))
            .assertIsDisplayed().performTextReplacement(text = "")
    }

    @Test
    fun checkNewPasswordErrors() {
        runBlocking { waitOutLoadDelay() }

        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagOldPasswordField))
            .assertIsDisplayed().performTextReplacement(text = oldPassword)

        // Fill in new password
        passwordValidation(
            testRule = testRule,
            buttonToPress = testRule.activity.getString(R.string.save),
            buttonShown = false
        )

        // PLace valid passwords and update
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordField))
            .assertIsDisplayed().performTextReplacement(text = newPassword)
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordMatchField))
            .assertIsDisplayed().performTextReplacement(text = newPassword)

        // Check fields have bee updated
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagOldPasswordField))
            .assertIsDisplayed().performTextReplacement(text = "")
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordField))
            .assertIsDisplayed().performTextReplacement(text = "")
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordMatchField))
            .assertIsDisplayed().performTextReplacement(text = "")
    }

    @Test
    fun checkPasswordMatch() = runTest {
        runBlocking { waitOutLoadDelay() }

        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagOldPasswordField))
            .assertIsDisplayed().performTextReplacement(text = oldPassword)

        // Fill in match password
        passwordMatchValidation(
            testRule = testRule,
            buttonToPress = testRule.activity.getString(R.string.save),
            buttonShown = false
        )

        // PLace valid passwords and update
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordField))
            .assertIsDisplayed().performTextReplacement(text = newPassword)
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordMatchField))
            .assertIsDisplayed().performTextReplacement(text = newPassword)

        // Check fields have bee updated
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagOldPasswordField))
            .assertIsDisplayed().performTextReplacement(text = "")
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordField))
            .assertIsDisplayed().performTextReplacement(text = "")
        testRule.onNodeWithTag(testTag = TestTagPasswordChangeExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPasswordMatchField))
            .assertIsDisplayed().performTextReplacement(text = "")
    }
}