package com.dbad.justintime.f_profile.presentation.profile

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextReplacement
import com.dbad.justintime.R
import com.dbad.justintime.core.AuthTestingRepo
import com.dbad.justintime.core.domain.use_case.ValidateDate
import com.dbad.justintime.core.domain.use_case.ValidateEmail
import com.dbad.justintime.core.domain.use_case.ValidatePassword
import com.dbad.justintime.core.domain.use_case.ValidatePhoneNumber
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationExpandableField
import com.dbad.justintime.core.presentation.util.TestTagEmailField
import com.dbad.justintime.core.presentation.util.TestTagEmergencyContactExpandableField
import com.dbad.justintime.core.presentation.util.TestTagMainPreferredContactMethodField
import com.dbad.justintime.core.presentation.util.TestTagNameField
import com.dbad.justintime.core.presentation.util.TestTagPhoneNumberField
import com.dbad.justintime.core.presentation.util.TestTagPreferredContactMethodField
import com.dbad.justintime.core.presentation.util.TestTagPreferredName
import com.dbad.justintime.core.presentation.util.TestTagProfileSaveButton
import com.dbad.justintime.core.presentation.util.TestTagUserInformationExpandableField
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_local_db.domain.model.util.PreferredContactMethod
import com.dbad.justintime.f_profile.data.ProfileRepositoryTestingImplementation.Companion.generateProfileTestRepo
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository
import com.dbad.justintime.f_profile.domain.use_case.AddNewUser
import com.dbad.justintime.f_profile.domain.use_case.CheckUserNotExist
import com.dbad.justintime.f_profile.domain.use_case.ClearDatabase
import com.dbad.justintime.f_profile.domain.use_case.GetEmergencyContact
import com.dbad.justintime.f_profile.domain.use_case.GetEmployee
import com.dbad.justintime.f_profile.domain.use_case.GetUser
import com.dbad.justintime.f_profile.domain.use_case.ProfileUseCases
import com.dbad.justintime.f_profile.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.f_profile.domain.use_case.UpsertEmployee
import com.dbad.justintime.f_profile.domain.use_case.UpsertUser
import com.dbad.justintime.f_profile.presentation.ProfileTestingNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileScreenUserEventsTesting {

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    suspend fun waitOutLoadDelay() {
        delay(timeMillis = 5000L)
    }

    @Before
    fun reset() = runTest {
        val profileRepo: ProfileRepository = generateProfileTestRepo()

        val useCases = ProfileUseCases(
            getUser = GetUser(repository = profileRepo),
            upsertUser = UpsertUser(repository = profileRepo),
            getEmployee = GetEmployee(repository = profileRepo),
            upsertEmployee = UpsertEmployee(repository = profileRepo),
            getEmergencyContact = GetEmergencyContact(repository = profileRepo),
            upsertEmergencyContact = UpsertEmergencyContact(repository = profileRepo),
            checkUserNotExist = CheckUserNotExist(repository = profileRepo),
            addNewUser = AddNewUser(repository = profileRepo),
            clearDatabase = ClearDatabase(repository = profileRepo),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validateDate = ValidateDate()
        )

        testRule.setContent {
            ProfileTestingNavController(
                useCases = useCases,
                authUser = AuthTestingRepo(user = User(), loggedIn = true)
            )
        }
    }

    @Test
    fun checkFieldsDisplayed() = runTest {
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagNameField)).assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPreferredName)).assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagEmailField)).assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPhoneNumberField))
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagMainPreferredContactMethodField))
            .onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPreferredContactMethodField))
            .assertIsDisplayed()
    }

    @Test
    fun collapseExpandInformationArea() {
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).performClick()
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagEmergencyContactExpandableField).performClick()
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagCompanyInformationExpandableField).performClick()
            .assertIsDisplayed()
    }

    @Test
    fun checkNameField() {
        val expectedName = "Cassandra Negrete"

        runBlocking { waitOutLoadDelay() }

        // Check the name is displayed & clear it
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagNameField)).assertIsDisplayed()
            .assertTextContains(value = expectedName)
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagNameField)).assertIsDisplayed()
            .performTextClearance()

        // Check that error is raised when no name is present
        testRule.onNodeWithTag(testTag = TestTagProfileSaveButton).assertIsDisplayed()
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasText(text = testRule.activity.getString(R.string.noNameProvided)))
            .assertIsDisplayed()

        // Re-add the name & check it is displayed
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagNameField))
            .performTextReplacement(text = expectedName)
        testRule.onNodeWithTag(testTag = TestTagProfileSaveButton).assertIsDisplayed()
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagNameField)).assertIsDisplayed()
            .assertTextContains(value = expectedName)
    }

    @Test
    fun checkPreferredNameField() {
        val newValue = "Cassandra"

        runBlocking { waitOutLoadDelay() }

        // Check default value within pref name field
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPreferredName)).assertIsDisplayed()
            .assertTextContains(value = "")

        // Update value within pref name field
        // Check save button is displayed
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPreferredName)).assertIsDisplayed()
            .performTextReplacement(text = newValue)
        testRule.onNodeWithTag(testTag = TestTagProfileSaveButton).assertIsDisplayed()
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagProfileSaveButton).assertIsNotDisplayed()

        // Check value has been updated
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPreferredName)).assertIsDisplayed()
            .assertTextContains(value = newValue)
    }

    @Test
    fun checkEmailField() {
        val expectedValue = "cassandra.negrete@justintime.com"

        runBlocking { waitOutLoadDelay() }

        // Check email value is expected & clear it
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagEmailField)).assertIsDisplayed()
            .assertTextContains(value = expectedValue)
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagEmailField)).assertIsDisplayed()
            .performTextClearance()

        // Check that error is raised when no email is present
        testRule.onNodeWithTag(testTag = TestTagProfileSaveButton).assertIsDisplayed()
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasText(text = testRule.activity.getString(R.string.invalidEmailError)))
            .assertIsDisplayed()

        // Re-add email & check it is displayed
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagEmailField))
            .performTextReplacement(text = expectedValue)
        testRule.onNodeWithTag(testTag = TestTagProfileSaveButton).assertIsDisplayed()
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagEmailField)).assertIsDisplayed()
            .assertTextContains(value = expectedValue)
    }

    @Test
    fun checkPhoneNumber() {
        val expectedValue = "07573881133"

        runBlocking { waitOutLoadDelay() }

        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPhoneNumberField))
            .assertIsDisplayed().assertTextContains(value = expectedValue)
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPhoneNumberField))
            .performTextClearance()

        testRule.onNodeWithTag(testTag = TestTagProfileSaveButton).assertIsDisplayed()
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasText(text = testRule.activity.getString(R.string.invalidPhoneNumb)))
            .assertIsDisplayed()

        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPhoneNumberField))
            .performTextReplacement(text = expectedValue)
        testRule.onNodeWithTag(testTag = TestTagProfileSaveButton).assertIsDisplayed()
            .performClick()
        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPhoneNumberField))
            .assertIsDisplayed().assertTextContains(value = expectedValue)
    }

    @Test
    fun checkPreferredContactMethod() {
        runBlocking { waitOutLoadDelay() }

        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagMainPreferredContactMethodField))
            .assertIsDisplayed()
            .assertTextContains(value = testRule.activity.getString(PreferredContactMethod.PHONE.stringVal))

        testRule.onNodeWithTag(testTag = TestTagUserInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagMainPreferredContactMethodField))
            .onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagPreferredContactMethodField))
            .assertIsDisplayed().performClick()

        testRule.onNodeWithText(text = testRule.activity.getString(PreferredContactMethod.EMAIL.stringVal))
            .assertIsDisplayed()
        testRule.onAllNodesWithText(text = testRule.activity.getString(PreferredContactMethod.PHONE.stringVal))
            .onFirst().assertIsDisplayed()
    }
}