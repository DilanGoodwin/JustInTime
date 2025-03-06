package com.dbad.justintime.f_login_register.domain.model.util

import androidx.annotation.StringRes
import com.dbad.justintime.R

enum class Relation(@StringRes val stringVal: Int) {
    MOTHER(R.string.mother),
    FATHER(R.string.father),
    SIBLING(R.string.sibling),
    SIGNIFICANT_OTHER(R.string.significationOther),
    CARER(R.string.carer),
    OTHER(R.string.other),
    NONE(0)
}