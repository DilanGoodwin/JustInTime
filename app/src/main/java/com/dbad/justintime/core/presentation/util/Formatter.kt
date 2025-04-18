package com.dbad.justintime.core.presentation.util

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val DATE_FORMATTER = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

fun formatDateToString(dateLong: Long?): String {
    if (dateLong != null) {
        return DATE_FORMATTER.format(dateLong)
    }
    return ""
}

fun formatStringToDate(date: String): Date? {
    if (date.isNotEmpty()) {
        return DATE_FORMATTER.parse(date)
    }
    return null
}