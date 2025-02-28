package com.dbad.justintime.f_login_register.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmergencyContact(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    val name: String = "",
    val preferredName: String = "",
    val phone: String = "",
    val preferredContactMethod: String = "",
    val relation: String = ""
)