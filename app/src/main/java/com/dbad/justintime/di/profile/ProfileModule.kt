package com.dbad.justintime.di.profile

import com.dbad.justintime.f_login_register.data.data_source.UserDatabaseRegisterLogin
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository
import com.dbad.justintime.f_profile.domain.use_case.ProfileUseCases

interface ProfileModule {
    val dataStore: UserDatabaseRegisterLogin
    val profileRepository: ProfileRepository
    val useCases: ProfileUseCases
}