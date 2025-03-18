package com.dbad.justintime.f_profile.presentation.profile

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dbad.justintime.core.domain.use_case.ValidateDate
import com.dbad.justintime.core.domain.use_case.ValidateEmail
import com.dbad.justintime.core.domain.use_case.ValidatePassword
import com.dbad.justintime.core.domain.use_case.ValidatePhoneNumber
import com.dbad.justintime.f_profile.data.ProfileRepositoryTestingImplementation
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository
import com.dbad.justintime.f_profile.domain.use_case.GetEmergencyContact
import com.dbad.justintime.f_profile.domain.use_case.GetEmployee
import com.dbad.justintime.f_profile.domain.use_case.GetUser
import com.dbad.justintime.f_profile.domain.use_case.ProfileUseCases
import com.dbad.justintime.f_profile.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.f_profile.domain.use_case.UpsertEmployee
import com.dbad.justintime.f_profile.domain.use_case.UpsertUser
import com.dbad.justintime.f_profile.presentation.ProfileTestingNavController
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileScreenUserEventsTesting {

    @get:Rule
    val testRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun reset() = runTest {
        val profileRepo: ProfileRepository = ProfileRepositoryTestingImplementation()
        val useCases = ProfileUseCases(
            getUser = GetUser(repository = profileRepo),
            upsertUser = UpsertUser(),
            getEmployee = GetEmployee(repository = profileRepo),
            upsertEmployee = UpsertEmployee(),
            getEmergencyContact = GetEmergencyContact(repository = profileRepo),
            upsertEmergencyContact = UpsertEmergencyContact(),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validateDate = ValidateDate()
        )

        testRule.setContent { ProfileTestingNavController(useCases = useCases) }
    }

    @Test
    fun checkFieldsDisplayed() {
    }

    @Test
    fun collapseExpandInformationArea() {
    }

    @Test
    fun checkNameField() {
    }

    @Test
    fun checkPreferredNameField() {
    }

    @Test
    fun checkEmailField() {
    }

    @Test
    fun checkDateOfBirth() {
    }

    @Test
    fun checkPhoneNumber() {
    }

    @Test
    fun checkPreferredContactMethod() {
    }
}