package com.dbad.justintime.f_user_auth.domain.repository

import androidx.lifecycle.LiveData

interface AuthRepo {
    val authState: LiveData<Boolean>
    val testingMode: Boolean
    fun getEmail(): String
    fun loginUser(email: String, password: String)
    fun deleteUser()
    fun signUp(email: String, password: String)
    fun signOut()
}