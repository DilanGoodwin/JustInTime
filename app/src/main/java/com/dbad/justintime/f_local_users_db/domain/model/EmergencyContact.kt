package com.dbad.justintime.f_local_users_db.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dbad.justintime.f_local_users_db.domain.model.util.PreferredContactMethod
import com.dbad.justintime.f_local_users_db.domain.model.util.Relation
import com.dbad.justintime.f_local_users_db.domain.model.util.generateIdentifier

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
        fun generateUid(email: String, employeeUid: String): String {
            return generateIdentifier(identifier = email, extraValues = employeeUid)
        }

        fun EmergencyContact.toHashMap(): Map<String, Any> {
            return hashMapOf(
                "uid" to uid,
                "name" to name,
                "preferredName" to preferredName,
                "email" to email,
                "phone" to phone,
                "preferredContactMethod" to preferredContactMethod,
                "relation" to relation
            )
        }

        fun Map<String, Any>.toEmergencyContact(): EmergencyContact {
            return EmergencyContact(
                uid = this["uid"] as String,
                name = this["name"] as String,
                preferredName = this["preferredName"] as String,
                email = this["email"] as String,
                phone = this["phone"] as String,
                preferredContactMethod = this["preferredContactMethod"] as PreferredContactMethod,
                relation = this["relation"] as Relation
            )
        }
    }
}