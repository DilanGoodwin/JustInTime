package com.dbad.justintime.f_login_register.domain.util

import androidx.annotation.StringRes
import com.dbad.justintime.R

enum class PasswordErrors(@StringRes val errorCode: Int) {
    PASSWORD_BLANK(errorCode = R.string.passwordBlank),
    PASSWORD_LENGTH_UNDER_10(errorCode = R.string.passwordLength),
    PASSWORD_NO_LOWERCASE(errorCode = R.string.passwordNoLowercase),
    PASSWORD_NO_UPPERCASE(errorCode = R.string.passwordNoCapitals),
    PASSWORD_NO_DIGIT(errorCode = R.string.passwordNoDigits),
    PASSWORD_NO_SYMBOLS(errorCode = R.string.passwordNoSymbols),
    PASSWORD_WHITESPACE(errorCode = R.string.passwordWhitespace),
    PASSWORD_NONE(errorCode = 0);

    companion object {
        fun getError(errorCode: Int) = entries.first { it.errorCode == errorCode }
    }
}