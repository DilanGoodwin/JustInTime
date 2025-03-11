package com.dbad.justintime.f_login_register.data.data_source

import com.dbad.justintime.core.domain.model.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserDatabaseRegisterLogin(testingMode: Boolean = false) {

    private var dataStore: FirebaseFirestore = Firebase.firestore
    private val userCollection = "user"

    init {
        if (testingMode) dataStore.useEmulator("10.0.2.2", 8080)
    }

    fun getUser(user: User): Flow<User> {
        return callbackFlow {
            dataStore.collection(userCollection).document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val sendUser = document.data!!.toUser()
                        trySend(sendUser)
                    } else {
                        trySend(User())
                    }
                }.addOnFailureListener { error ->
                    trySend(User())
                }
            awaitClose()
        }
    }

    //TODO upsert user
    //TODO upsert EmergencyContact
    //TODO upsert employee

//    fun upsertUser(user: User): Flow<Boolean> {
//        return callbackFlow {
//            dataStore.collection(userCollection).document(user.uid).set(user.toHashMap())
//                .addOnFailureListener { error ->
//                    trySend(false)
//                }.addOnSuccessListener {
//                    trySend(true)
//                }
//            awaitClose()
//        }
//    }

//    private fun User.toHashMap(): Map<String, Any> {
//        return hashMapOf(
//            "uid" to uid,
//            "email" to email,
//            "password" to password,
//            "employee" to employee
//        )
//    }

    private fun Map<String, Any>.toUser(): User {
        return User(
            uid = this["uid"] as String,
            email = this["email"] as String,
            password = this["password"] as String,
            employee = this["employee"] as String
        )
    }
}