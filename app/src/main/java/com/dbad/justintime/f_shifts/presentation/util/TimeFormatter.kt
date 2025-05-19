package com.dbad.justintime.f_shifts.presentation.util

fun formatTimeString(time: String): String {
    var returnTime = ""
    val timeSplit = time.split(":")

    returnTime += if (timeSplit[0].length != 2) {
        "0${timeSplit[0]}:"
    } else {
        "${timeSplit[0]}:"
    }

    returnTime += if (timeSplit[1].length != 2) {
        "${timeSplit[1]}0"
    } else {
        timeSplit[1]
    }

    return returnTime
}