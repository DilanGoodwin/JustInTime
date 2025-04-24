package com.dbad.justintime.f_login_register.domain.use_case

import com.dbad.justintime.f_login_register.domain.repository.UserRepository

class DeleteTmpUser(private val repo: UserRepository) {
    operator fun invoke() {
        repo.deleteTmpUser()
    }
}