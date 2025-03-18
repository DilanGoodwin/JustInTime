package com.dbad.justintime.f_login_register.data.data_source

import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact.Companion.toEmergencyContact
import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact.Companion.toHashMap
import com.dbad.justintime.f_local_users_db.domain.model.Employee
import com.dbad.justintime.f_local_users_db.domain.model.Employee.Companion.toEmployee
import com.dbad.justintime.f_local_users_db.domain.model.Employee.Companion.toHashMap
import com.dbad.justintime.f_local_users_db.domain.model.User
import com.dbad.justintime.f_local_users_db.domain.model.User.Companion.toHashMap
import com.dbad.justintime.f_local_users_db.domain.model.User.Companion.toUser
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserDatabaseRegisterLogin() {

    private var dataStore: FirebaseFirestore = Firebase.firestore
    private val userCollection = "user"
    private val emergencyContactCollection = "emergencyContact"
    private val employeeCollection = "employee"

    init {
        dataStore.useEmulator("10.0.2.2", 8080)
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
                }.addOnFailureListener {
                    trySend(User())
                }
            awaitClose()
        }
    }

    fun upsertUser(user: User): Flow<Boolean> {
        return callbackFlow {
            dataStore.collection(userCollection).document(user.uid).set(user.toHashMap())
                .addOnFailureListener {
                    trySend(false)
                }.addOnSuccessListener {
                    trySend(true)
                }
            awaitClose()
        }
    }

    fun getEmployee(employee: Employee): Flow<Employee> {
        return callbackFlow {
            dataStore.collection(employeeCollection).document(employee.uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val receivedEmployee = document.data!!.toEmployee()
                        trySend(receivedEmployee)
                    } else {
                        trySend(Employee())
                    }
                }.addOnFailureListener {
                    trySend(Employee())
                }
            awaitClose()
        }
    }

    fun upsertEmployee(employee: Employee): Flow<Boolean> {
        return callbackFlow {
            dataStore.collection(employeeCollection).document(employee.uid)
                .set(employee.toHashMap()).addOnFailureListener {
                    trySend(false)
                }.addOnSuccessListener {
                    trySend(true)
                }
            awaitClose()
        }
    }

    fun getEmergencyContact(emergencyContact: EmergencyContact): Flow<EmergencyContact> {
        return callbackFlow {
            dataStore.collection(emergencyContactCollection).document(emergencyContact.uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val receivedEmergencyContact = document.data!!.toEmergencyContact()
                        trySend(receivedEmergencyContact)
                    } else trySend(EmergencyContact())
                }.addOnFailureListener {
                    trySend(EmergencyContact())
                }
            awaitClose()
        }
    }

    fun upsertEmergencyContact(emergencyContact: EmergencyContact): Flow<Boolean> {
        return callbackFlow {
            dataStore.collection(emergencyContactCollection).document(emergencyContact.uid)
                .set(emergencyContact.toHashMap()).addOnFailureListener { error ->
                    trySend(false)
                }.addOnSuccessListener {
                    trySend(true)
                }
            awaitClose()
        }
    }
}