package com.dbad.justintime.f_local_db.domain.model.util

import androidx.annotation.StringRes
import com.dbad.justintime.R

enum class ShiftEventTypes(@StringRes val stringVal: Int) {
    SHIFTS(stringVal = R.string.shifts),
    HOLIDAY(stringVal = R.string.holiday),
    UNAVAILABILITY(stringVal = R.string.unavailability);

    companion object {
        fun Long.toShiftEventTypes(): ShiftEventTypes {
            return ShiftEventTypes.entries[this.toInt()]
        }

        fun ShiftEventTypes.toLong(): Long {
            return this.ordinal.toLong()
        }
    }
}