package com.dbad.justintime.di.profile

import com.dbad.justintime.core.domain.use_case.ValidateDate
import com.dbad.justintime.core.domain.use_case.ValidateEmail
import com.dbad.justintime.core.domain.use_case.ValidatePassword
import com.dbad.justintime.core.domain.use_case.ValidatePhoneNumber
import com.dbad.justintime.f_local_db.domain.use_case.LocalDatabaseUseCases
import com.dbad.justintime.f_profile.data.repository.ProfileRepositoryImplementation
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository
import com.dbad.justintime.f_profile.domain.use_case.GetEmergencyContact
import com.dbad.justintime.f_profile.domain.use_case.GetEmployee
import com.dbad.justintime.f_profile.domain.use_case.GetUser
import com.dbad.justintime.f_profile.domain.use_case.ProfileUseCases
import com.dbad.justintime.f_profile.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.f_profile.domain.use_case.UpsertEmployee
import com.dbad.justintime.f_profile.domain.use_case.UpsertUser

class ProfileModuleImplementation(private val localDatabase: LocalDatabaseUseCases) :
    ProfileModule {
    override val profileRepository: ProfileRepository =
        ProfileRepositoryImplementation(localDatabase = localDatabase)
    override val useCases: ProfileUseCases = ProfileUseCases(
        getUser = GetUser(repository = profileRepository),
        upsertUser = UpsertUser(repository = profileRepository),
        getEmployee = GetEmployee(repository = profileRepository),
        upsertEmployee = UpsertEmployee(repository = profileRepository),
        getEmergencyContact = GetEmergencyContact(repository = profileRepository),
        upsertEmergencyContact = UpsertEmergencyContact(repository = profileRepository),
        validateEmail = ValidateEmail(),
        validatePassword = ValidatePassword(),
        validatePhoneNumber = ValidatePhoneNumber(),
        validateDate = ValidateDate()
    )
}