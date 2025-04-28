package com.dbad.justintime.f_profile.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository

class AddNewUser(private val repository: ProfileRepository) {
    suspend operator fun invoke(user: User) {
        repository.addNewUser(user = user)
    }
}