package com.dbad.justintime.f_local_users_db.domain.model.util

import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

private const val ALGORITHM: String = "PBKDF2WithHmacSHA256"
private const val ITERATIONS: Int = 97672
private const val KEY_LENGTH: Int = 256

fun generateIdentifier(identifier: String, extraValues: String = "uid"): String {
    val identifierArray = identifier.toCharArray()
    val secretKeyGeneration = SecretKeyFactory.getInstance(ALGORITHM)
    val specification =
        PBEKeySpec(identifierArray, extraValues.toByteArray(), ITERATIONS, KEY_LENGTH)
    val generateSpecification = secretKeyGeneration.generateSecret(specification)
    val hashKey = SecretKeySpec(generateSpecification.encoded, "AES")
    return Base64.getEncoder().encodeToString(hashKey.encoded).replace("/", "")
}