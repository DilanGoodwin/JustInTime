package com.dbad.justintime.f_login_register.data

import com.dbad.justintime.f_local_datastore.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserPreferencesTestingImplementation(passedState: String = "") : UserPreferencesRepository {
    private var _currentState = passedState

    override val tokenFlow: Flow<String> = flowOf(_currentState)

    override suspend fun getLoginToken(): String {
        return _currentState
    }

    override suspend fun updateLoginToken(token: String) {
        _currentState = token
    }

    override suspend fun clearLoginToken() {
        _currentState = ""
    }
}