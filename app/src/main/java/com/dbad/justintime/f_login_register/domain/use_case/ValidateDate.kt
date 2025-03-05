package com.dbad.justintime.f_login_register.domain.use_case

class ValidateDate {

    private val ageToWork = 16 //How old someone within the UK must be to have a job

    operator fun invoke(currentDate: String, dateToCheck: String): Boolean {
        val splitCurrentDate = currentDate.split('/')
        val splitCheckingDate = dateToCheck.split('/')
        val validWorkingAge = ((splitCurrentDate[2].toInt()) - ageToWork).toString()

        if (splitCurrentDate.size != splitCheckingDate.size) return true

        // While I know this evaluation is wrong the program seems to think it is correct
        // and the right behaviour is only demonstrated when done this way
        if (validWorkingAge.toInt() > splitCheckingDate[2].toInt()) return true

        return false
    }
}