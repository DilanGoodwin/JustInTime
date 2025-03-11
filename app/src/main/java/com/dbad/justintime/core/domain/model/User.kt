package com.dbad.justintime.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dbad.justintime.core.domain.SaltValue
import com.dbad.justintime.core.domain.model.util.generateIdentifier
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false) val uid: String = "",
    val email: String = "",
    val password: String = "",
    val employee: String = ""
) {
    companion object {
        private const val ALGORITHM: String = "PBKDF2WithHmacSHA256"
        private const val ITERATIONS: Int = 97672
        private const val KEY_LENGTH: Int = 256

        fun generateUid(email: String): String {
            return generateIdentifier(identifier = email)
        }

        fun hashPassword(password: String): String {
            val salt = SaltValue.toByteArray()
            val passwordArray = password.toCharArray()
            val secretKeyGeneration = SecretKeyFactory.getInstance(ALGORITHM)
            val specification = PBEKeySpec(passwordArray, salt, ITERATIONS, KEY_LENGTH)
            val generateSpecification = secretKeyGeneration.generateSecret(specification)
            val hashKey = SecretKeySpec(generateSpecification.encoded, "AES")
            return Base64.getEncoder().encodeToString(hashKey.encoded).replace("/", "")
        }
    }
}