package com.dbad.justintime

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dbad.justintime.di.local_database.LocalDatabaseModule
import com.dbad.justintime.di.local_database.LocalDatabaseModuleImplementation
import com.dbad.justintime.di.login_register.LoginRegisterModule
import com.dbad.justintime.di.login_register.LoginRegisterModuleImplementation
import com.dbad.justintime.di.profile.ProfileModule
import com.dbad.justintime.di.profile.ProfileModuleImplementation
import com.dbad.justintime.di.shifts.ShiftsModule
import com.dbad.justintime.di.shifts.ShiftsModuleImplementation
import com.dbad.justintime.f_remoteDB.domain.model.BackgroundSync
import com.dbad.justintime.f_user_auth.data.data_source.UserAuthConnection
import java.util.concurrent.TimeUnit

class App : Application() {
    companion object {
        lateinit var localDatabase: LocalDatabaseModule
        lateinit var loginRegister: LoginRegisterModule
        lateinit var shifts: ShiftsModule
        lateinit var profile: ProfileModule
        lateinit var authUser: UserAuthConnection
    }

    /**
     * onCreate
     */
    override fun onCreate() {
        // Initialise all items
        super.onCreate()
        authUser = UserAuthConnection()
        localDatabase = LocalDatabaseModuleImplementation(context = this, auth = authUser)
        loginRegister = LoginRegisterModuleImplementation(
            localDatabase = localDatabase.useCases,
            auth = authUser
        )
        shifts = ShiftsModuleImplementation(localDatabase = localDatabase.useCases)
        profile = ProfileModuleImplementation(localDatabase = localDatabase.useCases)

        // Start work manager for background operations
        val workerConstraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val backgroundSync = PeriodicWorkRequestBuilder<BackgroundSync>(
            repeatInterval = 30,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        ).setConstraints(constraints = workerConstraints).build()
        WorkManager.getInstance(context = this).enqueue(request = backgroundSync)
    }
}