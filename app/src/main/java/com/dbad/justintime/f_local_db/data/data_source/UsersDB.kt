package com.dbad.justintime.f_local_db.data.data_source

import android.content.Context
import android.util.Base64
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dbad.justintime.f_local_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_user_auth.domain.repository.AuthRepo
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [User::class, Employee::class, EmergencyContact::class],
    version = 1,
    exportSchema = false
)
abstract class UsersDB : RoomDatabase() {
    abstract val dao: UsersDao

    companion object {
        private const val DB_NAME = "users.db"
        private var currentInstance: UsersDB? = null

        fun getInstance(context: Context, auth: AuthRepo): UsersDB {
            synchronized(this) {
                var instance = currentInstance

                if (instance == null) {
                    val passCode = Base64.decode(auth.getUid(), Base64.DEFAULT)
                    val factoryHelper = SupportFactory(passCode)

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDB::class.java,
                        DB_NAME
                    ).openHelperFactory(factoryHelper).build()
                    currentInstance = instance
                }
                return instance
            }
        }
    }
}