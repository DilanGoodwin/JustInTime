package com.dbad.justintime.f_remoteDB.domain.model

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dbad.justintime.App
import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.model.Employee.Companion.toEmployee
import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.Event.Companion.toEvent
import com.dbad.justintime.f_local_db.domain.model.Person
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_local_db.domain.repository.LocalDatabaseRepository
import com.dbad.justintime.f_user_auth.data.data_source.UserAuthConnection
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.runBlocking

//private const val NAME = "RemoteDatabaseSync"

class BackgroundSync(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val authUser: UserAuthConnection = App.authUser
    private val localDB: LocalDatabaseRepository = App.localDatabase.localDatabaseRepo

    private var dataStore: FirebaseFirestore = Firebase.firestore
    private val eventsCollection = "events"
    private val employeeCollection = "employee"

    override suspend fun doWork(): Result {
        Log.d("BackgroundSync", "Started Background Sync Operation")
        var successfulOperation = true

        if (authUser.authState.value!!) {
            // Get Details of User
            var userData =
                localDB.getUser(user = User(uid = User.generateUid(email = authUser.getEmail())))
            var employeeData = localDB.getEmployee(employee = Employee(uid = userData.employee))

            // Update Events
            dataStore.collection(eventsCollection).get().addOnSuccessListener { events ->
                Log.d("BackgroundSync", "Received Events Successfully")

                for (event in events) {
                    val eventData = event.data.toEvent() // Convert event to model event
                    if ((employeeData.uid in Event.convertStringEmployees(eventData.employees)) || employeeData.isAdmin) {
                        runBlocking { localDB.upsertEvent(event = eventData) }
                    }
                }
            }.addOnFailureListener {
                Log.d("BackgroundSync", "Failed to get Events")
                successfulOperation = false
            }

            /*
            Update People
            If we are an Admin then we should create a list of people that we are able to assign shifts to
            This is done by cycling through all of the Employees filling in their name and employeeUid
            The uid is needed so that once the shift is assigned the user can download it

            If we are not an admin then we only need to add the current logged in user to the list of
            people so that when they create events we set the right employeeUid
             */
            if (employeeData.isAdmin) {
                dataStore.collection(employeeCollection).get().addOnSuccessListener { people ->
                    Log.d("BackgroundSync", "Received People Successfully")

                    for (person in people) {
                        val personData = person.data.toEmployee()
                        val newPerson =
                            Person(employeeUid = personData.uid, name = employeeData.name)
                        runBlocking { localDB.upsertPerson(person = newPerson) }
                    }
                }
                    .addOnFailureListener {
                        Log.d("BackgroundSync", "Failed to get People")
                        successfulOperation = false
                    }
            } else {
                val newPerson = Person(employeeUid = employeeData.uid, name = employeeData.name)
                runBlocking { localDB.upsertPerson(person = newPerson) }
            }

        }
        return if (successfulOperation) Result.success() else Result.failure()
    }
}