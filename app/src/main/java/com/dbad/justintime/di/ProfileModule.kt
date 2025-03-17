package com.dbad.justintime.di

import com.dbad.justintime.f_profile.domain.repository.ProfileRepository
import com.dbad.justintime.f_profile.domain.use_case.ProfileUseCases

interface ProfileModule {
    val profileRepository: ProfileRepository
    val useCases: ProfileUseCases
}