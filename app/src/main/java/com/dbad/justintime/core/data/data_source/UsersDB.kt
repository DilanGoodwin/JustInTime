package com.dbad.justintime.core.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dbad.justintime.core.domain.model.EmergencyContact
import com.dbad.justintime.core.domain.model.Employee
import com.dbad.justintime.core.domain.model.User

@Database(entities = [User::class, Employee::class, EmergencyContact::class], version = 1)
abstract class UsersDB : RoomDatabase() {
    abstract val dao: UsersDao

    companion object {
        private const val DB_NAME = "users.db"
        private var currentInstance: UsersDB? = null

        fun getInstance(context: Context): UsersDB {
            synchronized(this) {
                var instance = currentInstance

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDB::class.java,
                        DB_NAME
                    ).build()
                    currentInstance = instance
                }
                return instance
            }
        }
    }
}