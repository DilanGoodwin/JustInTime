package com.dbad.justintime.f_login_register.domain.model.util

import androidx.annotation.StringRes
import com.dbad.justintime.R

enum class ContractType(@StringRes val stringVal: Int) {
    PART_TIME(R.string.part_time),
    SALARY(R.string.salary),
    CONTRACTOR(R.string.contractor),
    OTHER(R.string.other)
}