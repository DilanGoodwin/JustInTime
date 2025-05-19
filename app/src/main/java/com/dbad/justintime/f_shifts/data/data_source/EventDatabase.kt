package com.dbad.justintime.f_shifts.data.data_source

import android.util.Log
import com.dbad.justintime.core.presentation.util.formatStringToDate
import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.Event.Companion.toEvent
import com.dbad.justintime.f_local_db.domain.model.Event.Companion.toHashMap
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Date

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

    /*
    Check that the user do not already have an event that clashes with the provided event
    true - an event already exists
    false - event does not exist
     */
    fun validateExternalUsersEvents(newEvent: Event, person: String): Flow<Boolean> {
        return callbackFlow {
            dataStore.collection(eventsCollection).get().addOnSuccessListener { events ->
                Log.d(
                    "EventDatabase",
                    "Checking an event does not already exist within the database"
                )

                for (event in events) {
                    val eventData = event.data.toEvent()
                    if (person in Event.convertStringEmployees(eventData.employees) && eventData.approved) {
                        trySend(
                            validateEventDatesNotClash(
                                foundStartDate = formatStringToDate(eventData.startDate),
                                foundEndDate = formatStringToDate(eventData.endDate),
                                newStartDate = formatStringToDate(newEvent.startDate),
                                newEndDate = formatStringToDate(newEvent.endDate)
                            )
                        )
                    }
                }
                trySend(false)
            }
        }
    }

    fun validateEventDatesNotClash(
        foundStartDate: Date?, // Remote database event start date to check
        foundEndDate: Date?, // Remote database event end date to check
        newStartDate: Date?, // New event start date to check
        newEndDate: Date? // New event end date to check
    ): Boolean {
        return !((foundStartDate?.after(newEndDate) == true && foundEndDate?.after(newEndDate) == true) ||
                (foundStartDate?.before(newStartDate) == true && foundEndDate?.before(newStartDate) == true))
    }
}