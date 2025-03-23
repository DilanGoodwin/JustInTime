package com.dbad.justintime.f_local_users_db.domain.model.util

import androidx.annotation.StringRes
import com.dbad.justintime.R

enum class PreferredContactMethod(@StringRes val stringVal: Int) {
    PHONE(stringVal = R.string.phone),
    NONE(stringVal = 0),
    EMAIL(stringVal = R.string.email)
}

fun Long.toPreferredContactMethod(): PreferredContactMethod {
    return PreferredContactMethod.entries[this.toInt()]
}

fun PreferredContactMethod.toLong(): Long {
    return this.ordinal.toLong()
}
