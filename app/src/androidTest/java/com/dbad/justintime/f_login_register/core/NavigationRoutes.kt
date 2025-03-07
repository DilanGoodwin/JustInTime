package com.dbad.justintime.f_login_register.core

import kotlinx.serialization.Serializable

@Serializable
object LoginScreenRoute

@Serializable
object RegisterScreenRoute

@Serializable
data class UserDetailsRoute(val userUid: Int)

@Serializable
data class ProfileScreen(val userUid: Int)