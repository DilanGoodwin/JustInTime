package com.dbad.justintime.f_shifts.domain.model

import androidx.annotation.StringRes
import com.dbad.justintime.R

enum class ShiftEventTypes(@StringRes val stringVal: Int) {
    HOLIDAY(stringVal = R.string.holiday),
    BEREAVEMENT_LEAVE(stringVal = R.string.bereavement_leave),
    UNPAID_TIME_OFF(stringVal = R.string.unpaid_time_off),
    UNAVAILABILITY(stringVal = R.string.unavailability)
}

fun Long.toShiftEventTypes(): ShiftEventTypes {
    return ShiftEventTypes.entries[this.toInt()]
}

fun ShiftEventTypes.toLong(): Long {
    return this.ordinal.toLong()
}