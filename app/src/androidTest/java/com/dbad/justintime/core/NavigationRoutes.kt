package com.dbad.justintime.core

import kotlinx.serialization.Serializable

@Serializable
object LoginScreenRoute

@Serializable
object RegisterScreenRoute

@Serializable
data class UserDetailsRoute(val userUid: String)

@Serializable
object ProfileScreen