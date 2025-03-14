package com.dbad.justintime.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dbad.justintime.core.domain.model.util.ContractType
import com.dbad.justintime.core.domain.model.util.PreferredContactMethod
import com.dbad.justintime.core.domain.model.util.generateIdentifier

@Entity(tableName = "employee")
data class Employee(
    @PrimaryKey(autoGenerate = false) val uid: String = "",
    val name: String = "",
    val preferredName: String = "",
    val phone: String = "",
    val preferredContactMethod: PreferredContactMethod = PreferredContactMethod.PHONE,
    val dateOfBirth: String = "",
    val minimumHours: Int = 0,
    val emergencyContact: String = "",
    val isAdmin: Boolean = false,
    val companyName: String = "",
    val contractType: ContractType = ContractType.OTHER,
    val manager: String = "",
    val role: String = ""
) {
    companion object {
        fun generateUid(name: String, phone: String): String {
            return generateIdentifier(identifier = (name + phone))
        }

        fun Employee.toHashMap(): Map<String, Any> {
            return hashMapOf(
                "uid" to uid,
                "name" to name,
                "preferredName" to preferredName,
                "phone" to phone
            )
        }

        fun Map<String, Any>.toEmployee(): Employee {
            return Employee(
                uid = this["uid"] as String,
                name = this["name"] as String,
                preferredName = this["preferredName"] as String,
                phone = this["phone"] as String
            )
        }
    }
}