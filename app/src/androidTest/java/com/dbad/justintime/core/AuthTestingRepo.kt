package com.dbad.justintime.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_user_auth.domain.repository.AuthRepo

class AuthTestingRepo(private val user: User, loggedIn: Boolean = false) : AuthRepo {
    private val _authState = MutableLiveData<Boolean>(loggedIn)
    override val authState: LiveData<Boolean> = _authState
    override val testingMode = true

    override fun getUid(): String {
        return "TestingKey"
    }

    override fun getEmail(): String {
        return user.email
    }

    override fun loginUser(email: String, password: String) {
        if (user.email == email) _authState.value = true
    }

    override fun deleteUser() {
        _authState.value = false
    }

    override fun signUp(email: String, password: String) {
        if (user.email == email) _authState.value = true
    }

    override fun signOut() {
        _authState.value = false
    }
}