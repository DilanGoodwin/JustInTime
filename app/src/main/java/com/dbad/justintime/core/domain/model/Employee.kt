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
    }
}