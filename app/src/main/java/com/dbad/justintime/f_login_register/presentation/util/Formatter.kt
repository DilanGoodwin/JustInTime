package com.dbad.justintime.f_login_register.presentation.util

import android.icu.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dateLong: Long?): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    if (dateLong != null) {
        return formatter.format(dateLong)
    }
    return ""
}