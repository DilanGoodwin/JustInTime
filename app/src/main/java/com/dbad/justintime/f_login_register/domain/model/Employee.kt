package com.dbad.justintime.f_login_register.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "employee", foreignKeys = [ForeignKey(
        entity = EmergencyContact::class,
        parentColumns = ["uid"],
        childColumns = ["emergencyContact"]
    ), ForeignKey(entity = Employee::class, parentColumns = ["uid"], childColumns = ["manager"])]
)
data class Employee(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    val name: String = "",
    val preferredName: String = "",
    val phone: String = "",
    val preferredContactMethod: String = "",
    val dateOfBirth: String = "",
    val minimumHours: Int = 0,
    val emergencyContact: Int? = null,
    val contractType: String = "",
    val address: String = "",
    val manager: Int? = null,
    val role: String = ""
)