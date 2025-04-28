package com.dbad.justintime.f_profile.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class CheckUserNotExist(val repository: ProfileRepository) {
    operator fun invoke(user: User): Flow<User> {
        return repository.checkUserNotExist(user = user)
    }
}