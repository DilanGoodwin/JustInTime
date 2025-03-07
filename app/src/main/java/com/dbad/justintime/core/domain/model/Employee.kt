package com.dbad.justintime.core.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dbad.justintime.core.domain.model.util.ContractType
import com.dbad.justintime.core.domain.model.util.PreferredContactMethod

@Entity(
    tableName = "employee", foreignKeys = [ForeignKey(
        entity = EmergencyContact::class,
        parentColumns = ["uid"],
        childColumns = ["emergencyContact"],
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Employee::class,
        parentColumns = ["uid"],
        childColumns = ["manager"],
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class Employee(
    @ColumnInfo(name = "uid", index = true)
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    val name: String = "",
    val preferredName: String = "",
    val phone: String = "",
    val preferredContactMethod: PreferredContactMethod = PreferredContactMethod.PHONE,
    val dateOfBirth: String = "",
    val minimumHours: Int = 0,
    @ColumnInfo(name = "emergencyContact", index = true)
    val emergencyContact: Int? = null,
    val isAdmin: Boolean = false,
    val companyName: String = "",
    val contractType: ContractType = ContractType.OTHER,
    @ColumnInfo(name = "manager", index = true)
    val manager: Int? = null,
    val role: String = ""
)