package com.dbad.justintime.f_local_datastore.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dbad.justintime.f_local_datastore.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepositoryImplementation(private val context: Context) :
    UserPreferencesRepository {

    val LOGIN_TOKEN = stringPreferencesKey("login_token")

    val tokenFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[LOGIN_TOKEN] ?: ""
    }

    override suspend fun getLoginToken(): String {
        val preferences = context.dataStore.data.first()
        return preferences[LOGIN_TOKEN] ?: ""
    }

    override suspend fun updateLoginToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[LOGIN_TOKEN] = token
        }
    }

    override suspend fun clearLoginToken() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}