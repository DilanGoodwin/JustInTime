package com.dbad.justintime.core.domain.use_case

class ValidateDate {

    private val ageToWork = 16 //How old someone within the UK must be to have a job

    operator fun invoke(currentDate: String, dateToCheck: String): Boolean {
        if (dateToCheck.isBlank() || currentDate.isBlank()) return false
        if (dateToCheck.length != 10 || currentDate.length != 10) return false

        val splitCurrentDate: List<String> = currentDate.split('/')
        val splitCheckingDate: List<String> = dateToCheck.split('/')
        if (splitCurrentDate.size != splitCheckingDate.size) return false
        for (date in splitCheckingDate) {
            if (date.toInt() == 0) return false
        }

        val validWorkingAge = ((splitCurrentDate[2].toInt()) - ageToWork).toString()
        return validWorkingAge.toInt() >= splitCheckingDate[2].toInt()
    }
}