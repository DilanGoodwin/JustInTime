package com.dbad.justintime.f_shifts.domain.model

import androidx.annotation.StringRes
import com.dbad.justintime.R

enum class Days(@StringRes val stringVal: Int) {
    MON(stringVal = R.string.mon),
    TUE(stringVal = R.string.tue),
    WED(stringVal = R.string.wed),
    THU(stringVal = R.string.thu),
    FRI(stringVal = R.string.fri),
    SAT(stringVal = R.string.sat),
    SUN(stringVal = R.string.sun),
}