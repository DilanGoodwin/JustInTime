package com.dbad.justintime.f_local_datastore.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val tokenFlow: Flow<String>
    suspend fun getLoginToken(): String
    suspend fun updateLoginToken(token: String)
    suspend fun clearLoginToken()
}