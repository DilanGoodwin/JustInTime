package com.dbad.justintime.f_login_register.core

import kotlinx.serialization.Serializable

@Serializable
object LoginScreenRoute

@Serializable
object RegisterScreenRoute

@Serializable
data class UserDetailsRoute(val email: String, val password: String)

@Serializable
object ProfileScreen