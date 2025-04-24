package com.dbad.justintime

import kotlinx.serialization.Serializable

// Navigation Serialization objects
@Serializable
object LoginNav

@Serializable
object RegistrationNav

@Serializable
object LoginScreen

@Serializable
object RegisterScreen

@Serializable
data class UserDetailsInformation(val userUid: String)

@Serializable
object ProfileNav

@Serializable
object ProfileScreen

@Serializable
object ShiftNav

@Serializable
object ShiftScreen