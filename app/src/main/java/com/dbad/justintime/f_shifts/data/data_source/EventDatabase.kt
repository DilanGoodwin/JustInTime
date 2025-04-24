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
                Log.d("RemoteDatabase", "Event uploaded successfully")
            }.addOnFailureListener {
                Log.d("RemoteDatabase", "Event upload failed")
            }
    }

    fun deleteEvent(event: Event) {
        dataStore.collection(eventsCollection).document(event.uid).delete()
            .addOnSuccessListener {
                Log.d("RemoteDatabase", "Event successfully deleted")
            }.addOnFailureListener {
                Log.d("RemoteDatabase", "Event delete failed")
            }
    }
}