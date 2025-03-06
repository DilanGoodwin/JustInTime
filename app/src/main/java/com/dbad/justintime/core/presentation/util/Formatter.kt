package com.dbad.justintime.core.presentation.util

import android.icu.text.SimpleDateFormat
import java.util.Locale

val DATE_FORMATTER = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

fun formatDateToString(dateLong: Long?): String {
    if (dateLong != null) {
        return DATE_FORMATTER.format(dateLong)
    }
    return ""
}