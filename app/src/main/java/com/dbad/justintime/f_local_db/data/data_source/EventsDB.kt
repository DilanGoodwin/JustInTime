package com.dbad.justintime.f_local_db.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.Person

@Database(entities = [Event::class, Person::class], version = 1)
abstract class EventsDB : RoomDatabase() {
    abstract val dao: EventsDao

    companion object {
        private const val DB_NAME = "events.db"
        private var currentInstance: EventsDB? = null

        fun getInstance(context: Context): EventsDB {
            synchronized(this) {
                var instance = currentInstance

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EventsDB::class.java,
                        DB_NAME
                    ).build()
                    currentInstance = instance
                }
                return instance
            }
        }
    }
}