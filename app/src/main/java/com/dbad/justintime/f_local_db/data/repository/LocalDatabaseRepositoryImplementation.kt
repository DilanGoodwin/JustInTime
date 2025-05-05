package com.dbad.justintime.f_local_db.data.repository

import com.dbad.justintime.f_local_db.data.data_source.EventsDao
import com.dbad.justintime.f_local_db.data.data_source.UsersDao
import com.dbad.justintime.f_local_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.Person
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_local_db.domain.model.util.ShiftEventTypes
import com.dbad.justintime.f_local_db.domain.repository.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow

class LocalDatabaseRepositoryImplementation(
    private val eventsDao: EventsDao,
    private val usersDao: UsersDao
) : LocalDatabaseRepository {
    override suspend fun getUser(user: User): User {
        val tmpUser = usersDao.getUser(uid = user.uid)
        if (tmpUser == null) return User()
        return tmpUser
    }

    override suspend fun upsertUser(user: User) {
        usersDao.upsertUser(user = user)
    }

    override suspend fun getEmployee(employee: Employee): Employee {
        return usersDao.getEmployee(uid = employee.uid)
    }

    override suspend fun upsertEmployee(employee: Employee) {
        usersDao.upsertEmployee(employee = employee)
    }

    override suspend fun getEmergencyContact(emergencyContact: EmergencyContact): EmergencyContact {
        return usersDao.getEmergencyContact(uid = emergencyContact.uid)
    }

    override suspend fun upsertEmergencyContact(emergencyContact: EmergencyContact) {
        usersDao.upsertEmergencyContact(contact = emergencyContact)
    }

    override fun getEvents(type: ShiftEventTypes): Flow<List<Event>> {
        return eventsDao.getEvents(type = type)
    }

    override suspend fun upsertEvent(event: Event) {
        eventsDao.upsertEvents(event = event)
    }

    override suspend fun deleteEvent(event: Event) {
        eventsDao.deleteEvent(event = event)
    }

    override fun getPeople(): Flow<List<Person>> {
        return eventsDao.getPeople()
    }

    override suspend fun upsertPerson(person: Person) {
        eventsDao.upsertPerson(person = person)
    }

    override suspend fun clearLocalDatabase() {
        usersDao.deleteEmergencyContact()
        usersDao.deleteEmployee()
        usersDao.deleteUser()
        eventsDao.deleteEvents()
        eventsDao.deletePeople()
    }
}