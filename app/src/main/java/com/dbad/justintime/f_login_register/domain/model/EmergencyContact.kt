package com.dbad.justintime.f_login_register.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dbad.justintime.f_login_register.domain.model.util.PreferredContactMethod
import com.dbad.justintime.f_login_register.domain.model.util.Relation

@Entity(tableName = "emergency_contact")
data class EmergencyContact(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    val name: String = "",
    val preferredName: String = "",
    val email: String = "",
    val phone: String = "",
    val preferredContactMethod: PreferredContactMethod = PreferredContactMethod.PHONE,
    val relation: Relation = Relation.OTHER
)