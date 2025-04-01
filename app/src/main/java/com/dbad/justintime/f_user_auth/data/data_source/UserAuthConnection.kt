package com.dbad.justintime.f_user_auth.data.data_source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class UserAuthConnection {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<Boolean>()
    val authState: LiveData<Boolean> = _authState

    init {
        isAuthenticated()
    }

    private fun isAuthenticated() {
        _authState.value = auth.currentUser != null
    }

    fun getEmail(): String {
        return auth.currentUser?.email.toString()
    }

    fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) return

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            _authState.value = task.isSuccessful
        }
    }

    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            _authState.value = task.isSuccessful
        }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = false
    }
}