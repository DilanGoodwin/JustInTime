package com.dbad.justintime.f_local_db.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dbad.justintime.core.model.util.generateIdentifier
import com.dbad.justintime.f_local_db.domain.model.util.ContractType
import com.dbad.justintime.f_local_db.domain.model.util.PreferredContactMethod
import com.dbad.justintime.f_local_db.domain.model.util.toContractType
import com.dbad.justintime.f_local_db.domain.model.util.toLong
import com.dbad.justintime.f_local_db.domain.model.util.toPreferredContactMethod

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
        fun generateUid(userUid: String, name: String, phone: String): String {
            return generateIdentifier(identifier = (name + phone), extraValues = userUid)
        }

        fun Employee.toHashMap(): Map<String, Any> {
            return hashMapOf(
                "uid" to uid,
                "name" to name,
                "preferredName" to preferredName,
                "phone" to phone,
                "preferredContactMethod" to preferredContactMethod.toLong(),
                "dateOfBirth" to dateOfBirth,
                "minimumHours" to minimumHours,
                "emergencyContact" to emergencyContact,
                "isAdmin" to isAdmin,
                "companyName" to companyName,
                "contractType" to contractType.toLong(),
                "manager" to manager,
                "role" to role
            )
        }

        fun Map<String, Any>.toEmployee(): Employee {
            return Employee(
                uid = this["uid"] as String,
                name = this["name"] as String,
                preferredName = this["preferredName"] as String,
                phone = this["phone"] as String,
                preferredContactMethod = (this["preferredContactMethod"] as Long).toPreferredContactMethod(),
                dateOfBirth = this["dateOfBirth"] as String,
                minimumHours = (this["minimumHours"] as Long).toInt(),
                emergencyContact = this["emergencyContact"] as String,
                isAdmin = this["isAdmin"] as Boolean,
                companyName = this["companyName"] as String,
                contractType = (this["contractType"] as Long).toContractType(),
                manager = this["manager"] as String,
                role = this["role"] as String
            )
        }
    }
}