package com.dbad.justintime.f_local_db.data.data_source

import android.content.Context
import android.util.Base64
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_local_db.domain.model.Person
import com.dbad.justintime.f_user_auth.domain.repository.AuthRepo
import net.sqlcipher.database.SupportFactory

@Database(entities = [Event::class, Person::class], version = 1, exportSchema = false)
abstract class EventsDB : RoomDatabase() {
    abstract val dao: EventsDao

    companion object {
        private const val DB_NAME = "events.db"
        private var currentInstance: EventsDB? = null

        fun getInstance(context: Context, auth: AuthRepo): EventsDB {
            synchronized(this) {
                var instance = currentInstance

                if (instance == null) {
                    val passCode = Base64.decode(auth.getUid(), Base64.DEFAULT)
                    val factoryHelper = SupportFactory(passCode)

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EventsDB::class.java,
                        DB_NAME
                    ).openHelperFactory(factoryHelper).build()
                    currentInstance = instance
                }
                return instance
            }
        }
    }
}