package com.dbad.justintime.f_local_users_db.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dbad.justintime.f_local_users_db.domain.model.util.generateIdentifier

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false) val uid: String = "",
    val email: String = "",
    val employee: String = ""
) {
    companion object {
        fun generateUid(email: String): String {
            return generateIdentifier(identifier = email)
        }

        fun User.toHashMap(): Map<String, Any> {
            return hashMapOf(
                "uid" to uid,
                "email" to email,
                "employee" to employee
            )
        }

        fun Map<String, Any>.toUser(): User {
            return User(
                uid = this["uid"] as String,
                email = this["email"] as String,
                employee = this["employee"] as String
            )
        }
    }
}