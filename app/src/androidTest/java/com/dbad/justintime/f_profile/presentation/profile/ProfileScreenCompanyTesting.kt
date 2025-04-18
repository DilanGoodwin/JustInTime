package com.dbad.justintime.f_profile.presentation.profile

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEditable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.dbad.justintime.core.AuthTestingRepo
import com.dbad.justintime.core.domain.use_case.ValidateDate
import com.dbad.justintime.core.domain.use_case.ValidateEmail
import com.dbad.justintime.core.domain.use_case.ValidatePassword
import com.dbad.justintime.core.domain.use_case.ValidatePhoneNumber
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationCompanyNameField
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationExpandableField
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationManagerNameField
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationRole
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_local_db.domain.model.util.ContractType
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileScreenCompanyTesting {

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
                authUser = AuthTestingRepo(user = User(), loggedIn = true)
            )
        }

        testRule.onNodeWithTag(testTag = TestTagCompanyInformationExpandableField).performClick()
    }

    @Test
    fun checkFieldsDisplayed() {
        testRule.onNodeWithTag(testTag = TestTagCompanyInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagCompanyInformationCompanyNameField))
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagCompanyInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagCompanyInformationManagerNameField))
            .assertIsDisplayed()
        testRule.onNodeWithTag(testTag = TestTagCompanyInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagCompanyInformationRole))
            .assertIsDisplayed()
    }

    @Test
    fun checkCompanyName() {
        val companyName = "Testing Company .Co"

        runBlocking { waitOutLoadDelay() }

        testRule.onNodeWithTag(testTag = TestTagCompanyInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagCompanyInformationCompanyNameField))
            .assertIsDisplayed().assertTextContains(value = companyName)
        testRule.onNodeWithTag(testTag = TestTagCompanyInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagCompanyInformationCompanyNameField))
            .assert(matcher = isEditable())
    }

    @Test
    fun checkDirectReport() {
        val directReport = "Paul Smith"

        runBlocking { waitOutLoadDelay() }

        testRule.onNodeWithTag(testTag = TestTagCompanyInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagCompanyInformationManagerNameField))
            .assertIsDisplayed().assertTextContains(value = directReport)
    }

    @Test
    fun checkContractType() {
        val contractType = ContractType.SALARY

        runBlocking { waitOutLoadDelay() }

        testRule.onNodeWithTag(testTag = TestTagCompanyInformationExpandableField).onChildren()
            .filterToOne(matcher = hasText(text = testRule.activity.getString(contractType.stringVal)))
            .assertIsDisplayed()
    }

    @Test
    fun checkRole() {
        val role = "Tester"

        runBlocking { waitOutLoadDelay() }

        testRule.onNodeWithTag(testTag = TestTagCompanyInformationExpandableField).onChildren()
            .filterToOne(matcher = hasTestTag(testTag = TestTagCompanyInformationRole))
            .assertIsDisplayed().assertTextContains(value = role)
    }
}