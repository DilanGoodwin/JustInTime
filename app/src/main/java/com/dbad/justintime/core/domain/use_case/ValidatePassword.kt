package com.dbad.justintime.core.domain.use_case

import com.dbad.justintime.f_login_register.domain.util.PasswordErrors

/**
 * ValidatePassword
 * Ensures the entered password by the user contains:
 * * Length 10 or greater
 * * Lowercase character
 * * Uppercase character
 * * A digit
 * * A symbol
 * * No whitespace
 */
class ValidatePassword {
    operator fun invoke(password: String): PasswordErrors {
        if (password.isBlank()) return PasswordErrors.PASSWORD_BLANK
        if (password.firstOrNull { !it.isWhitespace() } == null) return PasswordErrors.PASSWORD_WHITESPACE
        if (password.filter { it.isLetter() }
                .firstOrNull { it.isLowerCase() } == null) return PasswordErrors.PASSWORD_NO_LOWERCASE
        if (password.filter { it.isLetter() }
                .firstOrNull { it.isUpperCase() } == null) return PasswordErrors.PASSWORD_NO_UPPERCASE
        if (password.firstOrNull { it.isDigit() } == null) return PasswordErrors.PASSWORD_NO_DIGIT
        if (password.firstOrNull { !it.isLetterOrDigit() } == null) return PasswordErrors.PASSWORD_NO_SYMBOLS
        if (password.length < 10) return PasswordErrors.PASSWORD_LENGTH_UNDER_10

        return PasswordErrors.PASSWORD_NONE
    }
}