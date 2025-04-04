package com.dbad.justintime.f_user_auth.data.data_source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dbad.justintime.f_user_auth.domain.repository.AuthRepo
import com.google.firebase.auth.FirebaseAuth

class UserAuthConnection : AuthRepo {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<Boolean>()
    override val authState: LiveData<Boolean> = _authState
    override val testingMode = false

    init {
        isAuthenticated()
    }

    private fun isAuthenticated() {
        _authState.value = auth.currentUser != null
    }

    override fun getEmail(): String {
        return auth.currentUser?.email.toString()
    }

    override fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) return

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            _authState.value = task.isSuccessful
        }
    }

    override fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            _authState.value = task.isSuccessful
        }
    }

    override fun signOut() {
        auth.signOut()
        _authState.value = false
    }
}