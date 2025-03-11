package com.dbad.justintime.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dbad.justintime.core.domain.model.util.PreferredContactMethod
import com.dbad.justintime.core.domain.model.util.Relation
import com.dbad.justintime.core.domain.model.util.generateIdentifier

@Entity(tableName = "emergency_contact")
data class EmergencyContact(
    @PrimaryKey(autoGenerate = false) val uid: String = "",
    val name: String = "",
    val preferredName: String = "",
    val email: String = "",
    val phone: String = "",
    val preferredContactMethod: PreferredContactMethod = PreferredContactMethod.PHONE,
    val relation: Relation = Relation.OTHER
) {
    companion object {
        fun generateUid(email: String): String {
            return generateIdentifier(identifier = email)
        }
    }
}