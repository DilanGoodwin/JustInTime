package com.dbad.justintime.f_shifts.data.data_source

import android.util.Log
import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.Event.Companion.toHashMap
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class EventDatabase() {

    private var dataStore: FirebaseFirestore = Firebase.firestore
    private val eventsCollection = "events"

    fun upsertEvent(event: Event) {
        dataStore.collection(eventsCollection).document(event.uid)
            .set(event.toHashMap())
            .addOnSuccessListener {
                Log.d("RemoteDatabase", "User data successfully uploaded")
            }.addOnFailureListener {
                Log.d("RemoteDatabase", "User data failed Upload")
            }
    }
}