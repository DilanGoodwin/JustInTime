package com.dbad.justintime.f_shifts.domain.use_case

class ValidateDate {

    operator fun invoke(currentDate: String, startDate: String, endDate: String): Boolean {
        if (startDate.isBlank() || endDate.isBlank() || currentDate.isBlank()) return false
        if (startDate.length != 10 || endDate.length != 10 || currentDate.length != 10) return false

        val splitCurrentDate: List<String> = currentDate.split('/')
        val splitStartDate: List<String> = startDate.split('/')
        val splitEndDate: List<String> = endDate.split('/')
        if ((splitCurrentDate.size != splitStartDate.size) || (splitCurrentDate.size != splitEndDate.size)) return false

        /*
        Check that the start date does not occur before the current date
        Check that the end date does not occur before the current date
         */
        val dateOccursBeforeCurrent = (checkDateAgainstCurrent(
            currentDate = splitCurrentDate,
            checkDate = splitStartDate
        ) && checkDateAgainstCurrent(
            currentDate = splitCurrentDate,
            checkDate = splitEndDate
        ))

        return dateOccursBeforeCurrent && (startDate == endDate || checkDateAgainstCurrent(
            currentDate = splitStartDate, checkDate = splitEndDate
        ))
    }

    private fun checkDateAgainstCurrent(
        currentDate: List<String>,
        checkDate: List<String>
    ): Boolean {
        for (date in checkDate) {
            if (date.toInt() == 0) return false
        }

        if (checkDate[2].toInt() < currentDate[2].toInt()) return false
        if ((checkDate[2].toInt() == currentDate[2].toInt()) && (checkDate[1].toInt() < currentDate[1].toInt())) return false
        if ((checkDate[2].toInt() == currentDate[2].toInt()) &&
            (checkDate[1].toInt() == currentDate[1].toInt()) &&
            (checkDate[0].toInt() < currentDate[0].toInt())
        ) return false

        return true
    }
}