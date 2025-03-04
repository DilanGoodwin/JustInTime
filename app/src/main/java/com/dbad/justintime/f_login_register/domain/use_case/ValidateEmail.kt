package com.dbad.justintime.f_login_register.domain.use_case

import android.util.Patterns

class ValidateEmail {
    operator fun invoke(email: String): Boolean {
        if (email.isBlank()) return false
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) return true

        return false
    }
}