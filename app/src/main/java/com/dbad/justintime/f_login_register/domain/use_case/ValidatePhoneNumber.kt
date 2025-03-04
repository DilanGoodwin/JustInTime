package com.dbad.justintime.f_login_register.domain.use_case

import android.util.Log

class ValidatePhoneNumber {
    operator fun invoke(phone: String): Boolean {
        if (phone.length < 10) return false

        if ((phone[0] == '7') && (phone.length == 10)) return true

        var substringPhone = phone.substring(0, 2)
        Log.d("PhoneValidation", "Substring value $substringPhone")
        if (substringPhone.contains(other = "07") && (phone.length == 11)) return true

        substringPhone = phone.substring(0, 5)
        Log.d("PhoneValidation", "Substring value $substringPhone")
        if (substringPhone.contains(other = "+440") && phone.length == 14) return true
        if (substringPhone.contains(other = "+44") && phone.length == 13) return true
        return false
    }
}