package com.dbad.justintime.f_profile.domain.use_case

import com.dbad.justintime.f_profile.domain.repository.ProfileRepository

class ClearDatabase(private val repository: ProfileRepository) {
    suspend operator fun invoke() {
        repository.clearDatabase()
    }
}