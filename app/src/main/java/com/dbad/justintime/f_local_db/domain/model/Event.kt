package com.dbad.justintime.f_local_db.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dbad.justintime.core.model.util.generateIdentifier
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes.Companion.toLong
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes.Companion.toShiftEventTypes

@Entity(tableName = Event.EVENT_TABLE_NAME)
data class Event(
    @PrimaryKey(autoGenerate = false) val uid: String = "",
    val type: ShiftEventTypes = ShiftEventTypes.SHIFTS,
    val startDate: String = "",
    val startTime: String = "",
    val endDate: String = "",
    val endTime: String = "",
    val rolePosition: String = "",
    val employees: String = "",
    val location: String = "",
    val approved: Boolean = false,
    val notes: String = ""
) {
    companion object {
        const val EVENT_TABLE_NAME = "events"

        /*
        Local database can only store primitive types so we need to convert to primitive type to
        store the data on the device should internet be lost and offline mode enabled
         */
        fun convertEmployeesString(employees: List<String>): String {
            return employees.joinToString(separator = "@")
        }

        /*
        Local database can only store primitive types so we need to convert from primitive type to
        array to perform operations on the individual data items.
         */
        fun convertStringEmployees(employees: String): List<String> {
            return employees.split("@")
        }

        fun generateUid(employeeId: String): String {
            val randStart = (Int.MIN_VALUE..Int.MAX_VALUE).random()
            val randEnd = (Int.MIN_VALUE..Int.MAX_VALUE).random()
            return generateIdentifier(
                identifier = employeeId,
                extraValues = "${randStart}ShiftEncoding${randEnd}"
            )
        }

        fun Event.toHashMap(): Map<String, Any> {
            return hashMapOf(
                "uid" to uid,
                "type" to type.toLong(),
                "startDate" to startDate.replace(oldChar = '/', newChar = ':'),
                "startTime" to startTime,
                "endDate" to endDate.replace(oldChar = '/', newChar = ':'),
                "endTime" to endTime,
                "rolePosition" to rolePosition,
                "employees" to employees,
                "location" to location,
                "approved" to approved,
                "notes" to notes
            )
        }

        /*
        Taking the mapping from the datastore and converting it back into our data class
         */
        fun Map<String, Any>.toEvent(): Event {
            return Event(
                uid = this["uid"] as String,
                type = (this["type"] as Long).toShiftEventTypes(),
                startDate = (this["startDate"] as String).replace(oldChar = ':', newChar = '/'),
                startTime = this["startTime"] as String,
                endDate = (this["endDate"] as String).replace(oldChar = ':', newChar = '/'),
                endTime = this["endTime"] as String,
                rolePosition = this["rolePosition"] as String,
                employees = this["employees"] as String,
                location = this["location"] as String,
                approved = this["approved"] as Boolean,
                notes = this["notes"] as String
            )
        }
    }
}