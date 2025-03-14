package com.dbad.justintime.f_local_datastore.domain.repository

interface UserPreferencesRepository {
    suspend fun getLoginToken(): String
    suspend fun updateLoginToken(token: String)
    suspend fun clearLoginToken()
}