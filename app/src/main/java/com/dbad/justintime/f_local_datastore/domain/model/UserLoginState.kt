package com.dbad.justintime.f_local_datastore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginState(val loginToken: String = "")