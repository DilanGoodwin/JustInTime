package com.dbad.justintime.core.domain.use_case

class ValidatePhoneNumber {
    operator fun invoke(phone: String): Boolean {
        if (phone.length < 10) return false

        if ((phone[0] == '7') && (phone.length == 10)) return true

        var substringPhone = phone.substring(0, 2)
        if (substringPhone.contains(other = "07") && (phone.length == 11)) return true

        substringPhone = phone.substring(0, 5)
        if (substringPhone.contains(other = "+440") && phone.length == 14) return true
        if (substringPhone.contains(other = "+44") && phone.length == 13) return true
        return false
    }
}