package com.dbad.justintime.f_login_register.domain.model.util

import androidx.annotation.StringRes
import com.dbad.justintime.R

enum class PreferredContactMethod(@StringRes val stringVal: Int) {
    NONE(stringVal = 0),
    PHONE(stringVal = R.string.phone),
    EMAIL(stringVal = R.string.email)
}