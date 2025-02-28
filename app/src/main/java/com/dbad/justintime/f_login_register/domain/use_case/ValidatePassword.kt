package com.dbad.justintime.f_login_register.domain.use_case

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
    operator fun invoke(password: String): Boolean {
        if (password.isBlank()) return false
        if (password.length < 10) return false
        if (password.filter { it.isLetter() }.firstOrNull { it.isLowerCase() } == null) return false
        if (password.filter { it.isLetter() }
                .firstOrNull { it.isUpperCase() } == null) return false
        if (password.firstOrNull { it.isDigit() } == null) return false
        if (password.firstOrNull { !it.isLetterOrDigit() } == null) return false
        if (password.firstOrNull { !it.isWhitespace() } == null) return false

        return true
    }
}