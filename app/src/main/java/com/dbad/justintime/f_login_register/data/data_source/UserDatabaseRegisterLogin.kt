package com.dbad.justintime.f_login_register.data.data_source

import android.util.Log
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

    fun upsertUser(user: User) {
        dataStore.collection(userCollection).document(user.uid)
            .set(user.toHashMap())
            .addOnSuccessListener {
                Log.d("RemoteDatabase", "User data successfully uploaded")
            }.addOnFailureListener {
                Log.d("RemoteDatabase", "User data failed Upload")
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

    fun upsertEmployee(employee: Employee) {
        dataStore.collection(employeeCollection).document(employee.uid)
            .set(employee.toHashMap())
            .addOnSuccessListener {
                Log.d("RemoteDatabase", "Employee data successfully uploaded")
            }.addOnFailureListener {
                Log.d("RemoteDatabase", "Employee data failed Upload")
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

    fun upsertEmergencyContact(emergencyContact: EmergencyContact) {
        dataStore.collection(emergencyContactCollection).document(emergencyContact.uid)
            .set(emergencyContact.toHashMap())
            .addOnSuccessListener {
                Log.d("RemoteDatabase", "EmergencyContact data successfully uploaded")
            }.addOnFailureListener {
                Log.d("RemoteDatabase", "EmergencyContact data failed Upload")
            }
    }
}