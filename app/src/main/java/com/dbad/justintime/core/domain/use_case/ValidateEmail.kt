package com.dbad.justintime.core.domain.use_case

import java.util.regex.Pattern

class ValidateEmail {
    private val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )

    operator fun invoke(email: String): Boolean {
        if (email.isBlank()) return false
        if (EMAIL_ADDRESS_PATTERN.matcher(email).matches()) return true

        return false
    }
}